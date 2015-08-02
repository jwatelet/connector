package connector.core

import akka.actor.{Props, ActorLogging, Actor}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.streaming.twitter.TwitterUtils

class StreamingActor extends Actor with ActorLogging {

  import StreamingActor._

  val ssc = new StreamingContext(Config.sparkConf, Seconds(10))

  val customersWithKeywords = new SQLContext(ssc.sparkContext).read.json(Config.jsonPath)//.format("jdbc")
    //.options(Config.optionsForRDBMS)
    //.load()
    //.map(row => (row.getString(0), List(row.getString(1))))
    .map(row => (row.getString(0), row.getSeq[String](1)))
    //.reduceByKey(_ ++ _)
    .persist()

  val stream = TwitterUtils.createStream(ssc, None, filters = customersWithKeywords.keys.collect())

  val userStatus = stream.map(status => (status.getUser.getId, status.getText.toLowerCase.split(" ")))

  userStatus.foreachRDD { rdd =>
    val cartesian = rdd.cartesian(customersWithKeywords) //((id, words), (customer, keywords))
    cartesian  /* notice that we now skip all power of `findConnections` (skipping keywords occurrences) function just for testing purpose */
      .map(x => (x._1._1, x._2._1, findConnections(x._1._2, x._2._2).sortWith(_._2 < _._2)))
      .filter(_._3.nonEmpty)
      .foreach {data =>
      println("************** BEGIN TWEET *************")
      println(s"user_id:$data._1, customer:$data._2 \n ${data._3.map(_._1).mkString(",")}")
      println("************** END TWEET *************")
    }
  }

  ssc.start()

  def receive = {
    case msg @ _ => log.info(s"$msg message received")
  }

  override def postStop() {
    ssc.stop()
  }
}

case object StreamingActor {

  /** Finds keywords in given words
    *
    * @param words seq of words
    * @param keywords seq of keywords
    * @return a map of keywords to the count of occurrences in given words
    */
  def findConnections(words: Seq[String], keywords: Seq[String]): Seq[(String, Int)] =
    for {
      keyword <- keywords
      occurs = words.map(word => if (word.contains(keyword)) 1 else 0).sum
      if occurs != 0
    } yield (keyword, occurs)
}

