package connector.core

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import connector.api.ApplicationActor
import connector.streaming.StreamingActor
import org.apache.spark.streaming.{Seconds, StreamingContext}
import spray.can.Http
import scala.concurrent.duration._

object Boot extends App {

  implicit val system = ActorSystem(Config.actorSystemName)
  implicit val timeout = Timeout(15.seconds)

  val ssc = new StreamingContext(Config.sparkConf, Seconds(10))

  val application = system.actorOf(Props[ApplicationActor], "connector-service")
  val streamer = system.actorOf(Props(classOf[StreamingActor], ssc), "spark-app")

  IO(Http) ? Http.Bind (
    listener = application,
    interface = Config.interface,
    port = Config.port
  )
}

