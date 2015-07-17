package connector.core

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import connector.api.ApplicationActor
import spray.can.Http
import scala.concurrent.duration._

object Boot extends App {

  implicit val system = ActorSystem("connector-system")
  implicit val timeout = Timeout(15.seconds)

  val application = system.actorOf(Props[ApplicationActor], "connector-service")
  val config = ConfigFactory.load()

  IO(Http) ? Http.Bind(
    listener = application,
    interface = config.getString("app.server.host"),
    port = config.getInt("app.server.port")
  )
}