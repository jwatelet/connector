import com.typesafe.config.ConfigFactory
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf

object ProcessingTweetsData {

  val config = ConfigFactory.load("app")
  val twitterConf = ConfigFactory.load("twitter")

  /**
   * Receives and then computes tweets data filtered by given major words
   *
   * @param args - list of major words
   */
  def main(args: Array[String]): Unit = {
    System.setProperty("twitter4j.oauth.consumerKey", twitterConf.getString("consumerKey"))
    System.setProperty("twitter4j.oauth.consumerSecret", twitterConf.getString("consumerSecret"))
    System.setProperty("twitter4j.oauth.accessToken", twitterConf.getString("accessToken"))
    System.setProperty("twitter4j.oauth.accessTokenSecret", twitterConf.getString("accessTokenSecret"))

    val lowerCasedArgs = args.map(_.toLowerCase)
    val sparkConf = new SparkConf()
      .setMaster(config.getString("master-uri"))
      .setAppName(config.getString("app-name"))
      .set("spark.cassandra.connection.host", config.getString("cassandra.host"))

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val stream = TwitterUtils.createStream(ssc, None, lowerCasedArgs)
    val userStatus = stream.flatMap(status => findKeyword(lowerCasedArgs, status.getText.toLowerCase.split(" ")))

    userStatus.foreachRDD { rdd =>
      // some business logic
    }

    ssc.start()
    ssc.awaitTermination()
  }

  /**
   * Finds a keyword that related to given sentence.
   *
   * @param keywords words to be looked for in sentence
   * @param sentence represented as sequence of words
   * @return option of a keyword related to sentence
   */
  def findKeyword(keywords: Seq[String], sentence: Seq[String]) =
    for {
      keyword <- keywords
      if sentence.exists(word => word.contains(keyword))
    } yield (keyword, sentence)
}