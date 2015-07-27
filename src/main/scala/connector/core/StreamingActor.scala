package connector.core

import akka.actor.{Props, ActorLogging, Actor}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.streaming.twitter.TwitterUtils

class StreamingActor extends Actor with ActorLogging {

  val ssc = new StreamingContext(Config.sparkConf, Seconds(2))
  val stream = TwitterUtils.createStream(ssc, None)
  val customersWithKeywords = new SQLContext(ssc.sparkContext).read.format("jdbc").options(Config.optionsForRDBMS).load()
    .map(row => (row.getString(0), List(row.getString(1))))
    .reduceByKey(_ ++ _) // (customer, [keywords...])

  ssc.start()

  def receive = {
    case msg @ _ => log.info(s"$msg message received")
  }

  override def postStop() {
    ssc.stop()
  }
}

