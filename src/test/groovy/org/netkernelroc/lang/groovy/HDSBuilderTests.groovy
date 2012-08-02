package org.netkernelroc.lang.groovy;

import groovy.xml.XmlUtil

import org.junit.Test
import org.netkernel.layer0.representation.impl.HDSFactory
import org.netkernel.layer0.util.HDSUtils

class HDSBuilderTests {

  @Test
  void simpleHdsTreeSuccess() {
    def builder = new HDSBuilder()

    def hds = new HDSBuilder().config {
      http {
        port 8080
        host "localhost"
        protocol "http"
      }
    }

    assert hds.getFirstValue("//http/port") == 8080
  }

  @Test
  void hdsBuilderWithAttributes() {

    def builder = new HDSBuilder()

    def hds = builder.modules {
      module(id: "lang-groovy") {
        spaces {
          space(id: "urn:org:netkernelroc:lang:groovy")
          space(id: "urn:org:netkernelroc:lang:groovy:doc")
        }
      }
    }

    assert hds.getFirstValue("//space/@id") == "urn:org:netkernelroc:lang:groovy"
  }

}
