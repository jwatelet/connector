package connector.core

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import connector.api.ApplicationActor
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}
import spray.can.Http
import scala.concurrent.duration._

object Boot extends App {

  import Configuration._

  implicit val system = ActorSystem(config.getString("app.actor-system.name"))
  implicit val timeout = Timeout(15.seconds)

  val sparkConf = new SparkConf()
    .setMaster(config.getString("app.spark.master-uri"))
    .setAppName(config.getString("app.spark.app-name"))

  val application = system.actorOf(Props[ApplicationActor], "connector-service")
  val spark = system.actorOf(Props(classOf[SparkActor], sparkConf, optionsForRDBMS), "spark-app")

  IO(Http) ? Http.Bind (
    listener = application,
    interface = config.getString("app.server.host"),
    port = config.getInt("app.server.port")
  )

}

object Configuration {

  val config = ConfigFactory.load()

  val optionsForRDBMS = (query: String) => Map (
    "driver" -> config.getString("app.database.driver"),
    "url" -> config.getString("app.database.url"),
    "dbtable" -> query // can be either table name or select query
  )
}