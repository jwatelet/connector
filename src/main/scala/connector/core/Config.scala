package connector.core

import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf

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

  lazy val sparkConf = new SparkConf()
    .setMaster(config.getString("app.spark.master-uri"))
    .setAppName(config.getString("app.spark.app-name"))
    .set("spark.cassandra.connection.host", config.getString("app.database.cassandra.host"))
}
