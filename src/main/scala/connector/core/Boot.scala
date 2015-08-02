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

  implicit val system = ActorSystem(Config.actorSystemName)
  implicit val timeout = Timeout(15.seconds)

  val application = system.actorOf(Props[ApplicationActor], "connector-service")
  val tweeter = system.actorOf(Props(classOf[StreamingActor]), "spark-app")

  IO(Http) ? Http.Bind (
    listener = application,
    interface = Config.interface,
    port = Config.port
  )
}

object Config {

  private val config = ConfigFactory.load()
  private val twitterConf = ConfigFactory.load("twitter")

  System.setProperty("twitter4j.oauth.consumerKey", twitterConf.getString("consumerKey"))
  System.setProperty("twitter4j.oauth.consumerSecret", twitterConf.getString("consumerSecret"))
  System.setProperty("twitter4j.oauth.accessToken", twitterConf.getString("accessToken"))
  System.setProperty("twitter4j.oauth.accessTokenSecret", twitterConf.getString("accessTokenSecret"))

  lazy val interface = config.getString("app.server.host")
  lazy val port = config.getInt("app.server.port")
  lazy val actorSystemName = config.getString("app.actor-system.name")
  lazy val jsonPath = config.getString("app.database.json-path")

  lazy val optionsForRDBMS = Map (
    "driver" -> config.getString("app.database.driver"),
    "url" -> config.getString("app.database.url"),
    "dbtable" -> config.getString("app.database.query")
  )

  lazy val sparkConf = new SparkConf()
    .setMaster(config.getString("app.spark.master-uri"))
    .setAppName(config.getString("app.spark.app-name"))
}