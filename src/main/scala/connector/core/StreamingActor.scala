package connector.core

import akka.actor.{Props, ActorLogging, Actor}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.streaming.twitter.TwitterUtils

class StreamingActor extends Actor with ActorLogging {

  val ssc = new StreamingContext(Config.sparkConf, Seconds(2))
  val sqlContext = new SQLContext(ssc.sparkContext)
  val stream = TwitterUtils.createStream(ssc, None)
  val db = sqlContext.read.format("jdbc").options(Config.optionsForRDBMS).load()

  ssc.start()

  def receive = {
    case msg @ _ => log.info(s"$msg message received")
  }

  override def postStop() {
    ssc.stop()
  }
}

