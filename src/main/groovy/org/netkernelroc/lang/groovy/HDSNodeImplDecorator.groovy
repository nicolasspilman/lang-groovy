package org.netkernelroc.lang.groovy

import org.netkernel.layer0.representation.impl.HDSNodeImpl
import org.netkernel.layer0.util.HDSXPath

class HDSNodeImplDecorator {

  static void decorate() {
    
    ExpandoMetaClass metaClass = new ExpandoMetaClass(HDSNodeImpl.class);
    
    metaClass.parent = {
      delegate.getParent()
    }
    
    
    metaClass.children = { 
      delegate.getChildren()
    }
    
    metaClass.depthFirst = {
      def xpathResult = []
      HDSXPath.eval(xpathResult, delegate, "//*")
      return xpathResult
    }
    
    metaClass.text = {
      delegate.getValue()
    }
    
    metaClass.name = {
      delegate.getName()
    }
    
    metaClass.propertyMissing = { String name ->
      def result
      // Borrowed from the GPathResult class
      if("..".equals(name)) {
        result = delegate.getParent()
      } else if("*".equals(name)) {
        result = delegate.getChildren()
      } else if("**".equals(name)) {
        result = delegate.depthFirst()
      } else {
        def xpathResult = []
        HDSXPath.eval(xpathResult, delegate, name)
        
        if(xpathResult.size() == 1) {
          // TODO: Need to decide if attributes should be treated any differently
          if(name.startsWith("@")) {
            result = xpathResult[0].getValue()
          } else {
            result = xpathResult[0]
          }
        } else {
          result = xpathResult
        }
      }
      
//      Well, as usual, the best place to find information is in the Groovy source itself.
//      The result of a parsing is a groovy.util.slurpersupport.GPathResult object.
//      
//      If you look at the source (plain java file), you'll see that the getProperty(string) method has the following special operators:
//      
//      ".." that returns the parent
//      "*" that returns all the children
//      "**" that act as a depth first loop
//      "@" that is used to access a property
//      the normal node accessor.
      
      

      

      
      return result
      
    }
    
    metaClass.initialize()
    
    GroovySystem.metaClassRegistry.setMetaClass(HDSNodeImpl.class, metaClass)
  }

}
