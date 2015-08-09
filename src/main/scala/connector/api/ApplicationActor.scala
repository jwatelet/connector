package connector.api

import spray.routing.{HttpService, HttpServiceActor}


class ApplicationActor extends HttpServiceActor with ConnectorAPI {
  def receive = runRoute(routes)
}

/* the root of all routes */
trait ConnectorAPI extends HttpService {

  val index = pathEndOrSingleSlash {
    get {
      getFromResource("app/index.html")
    }
  }

  val statics = getFromResourceDirectory("app") ~
    pathPrefix("bower_components") {
      getFromResourceDirectory("bower_components")
    }

  val routes = index ~ statics
}

