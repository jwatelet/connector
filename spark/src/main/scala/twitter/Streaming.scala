import com.typesafe.config.ConfigFactory
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf

object Streaming {

  val config = ConfigFactory.load("app")
  val twitterConf = ConfigFactory.load("twitter")

  def main(args: Array[String]): Unit = {
    System.setProperty("twitter4j.oauth.consumerKey", twitterConf.getString("consumerKey"))
    System.setProperty("twitter4j.oauth.consumerSecret", twitterConf.getString("consumerSecret"))
    System.setProperty("twitter4j.oauth.accessToken", twitterConf.getString("accessToken"))
    System.setProperty("twitter4j.oauth.accessTokenSecret", twitterConf.getString("accessTokenSecret"))

    val sparkConf = new SparkConf()
      .setMaster(config.getString("app.spark.master-uri"))
      .setAppName(config.getString("app.spark.app-name"))
      .set("spark.cassandra.connection.host", config.getString("cassandra.host"))

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val stream = TwitterUtils.createStream(ssc, None, List(args(0)))
    val userStatus = stream.map(status => (status.getId, status.getText.toLowerCase.split(" ")))

    userStatus.foreachRDD { rdd =>
      // some business logic
    }

    ssc.start()
    ssc.awaitTermination()
  }

}