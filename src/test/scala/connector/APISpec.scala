package connector

import connector.api.ConnectorAPI
import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest


class APISpec extends Specification with Specs2RouteTest with ConnectorAPI {

  def actorRefFactory = system

  "Connector API" should {
    "return a greeting for GET requests to the root path" in {
      Get() ~> routes ~> check {
        responseAs[String] must contain("Hello Connector")
      }
    }
  }
}
