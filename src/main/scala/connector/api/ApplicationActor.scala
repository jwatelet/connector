package connector.api

import connector.models.CassandraBuilder
import spray.routing.{HttpService, HttpServiceActor}


class ApplicationActor extends HttpServiceActor with ConnectorAPI {
  def receive = runRoute(routes)
}

trait ConnectorAPI extends HttpService {

  //CassandraBuilder.start
  //CassandraBuilder.createTable

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

