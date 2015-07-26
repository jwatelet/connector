package connector.core

import akka.actor.{Props, ActorLogging, Actor}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.streaming.twitter.TwitterUtils

class TweeterStreamingActor(conf: SparkConf,
                 sqlConf: String => Map[String, String]) extends Actor with ActorLogging {

  val ssc = new StreamingContext(conf, Seconds(2))
  val sqlContext = new SQLContext(ssc.sparkContext)
  val stream = TwitterUtils.createStream(ssc, None)

  ssc.start()

  def receive = {
    case msg @ _ => log.info(s"$msg message received")
  }

  def createDataFrameFromSQL(query: String): DataFrame =
    sqlContext.read.format("jdbc").options(sqlConf(query)).load()

  override def postStop() {
    ssc.stop()
  }
}

