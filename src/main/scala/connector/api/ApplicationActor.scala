package connector.api

import spray.routing.{HttpService, HttpServiceActor}


class ApplicationActor extends HttpServiceActor with ConnectorAPI {
  def receive = runRoute(routes)
}

/* the root of all routes */
trait ConnectorAPI extends HttpService {

  val index = pathEndOrSingleSlash {
    get {
      getFromResource("index.html")
    }
  }

  val routes = index ~ getFromResourceDirectory("assets")
}

