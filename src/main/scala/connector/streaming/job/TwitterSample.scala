package connector.streaming.job

import connector.core.Config
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.twitter.TwitterUtils

private[streaming] object TwitterSample extends StreamJob {

  protected def runJob(ssc: StreamingContext, args: Seq[Any]): Unit = {
    require(args.isEmpty)

    val customersWithKeywords = new SQLContext(ssc.sparkContext).read.json(Config.jsonPath)
      .map(row => (row.getString(0), row.getSeq[String](1))).persist()

    val stream = TwitterUtils.createStream(ssc, None, filters = customersWithKeywords.keys.collect())

    val userStatus = stream.map(status => (status.getUser.getId, status.getText.toLowerCase.split(" ")))

    userStatus.foreachRDD { rdd =>
      rdd
        .cartesian(customersWithKeywords)
        .map(x => (x._1._1, x._2._1, findConnections(x._1._2, x._2._2).sortWith(_._2 < _._2)))
        .filter(_._3.nonEmpty)
        //.someMethodForStoringData
    }
  }

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

case object TwitterSampleJobMessage extends JobMessage {
  val args: Seq[Any] = Seq.empty
}
