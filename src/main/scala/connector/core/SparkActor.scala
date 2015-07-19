package connector.core

import akka.actor.{ActorLogging, Actor}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{DataFrame, SQLContext}

class SparkActor(conf: SparkConf,
                 sqlConf: String => Map[String, String]) extends Actor with ActorLogging {

  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  def receive = {
    case msg @ _ => log.info(s"$msg message received")
  }

  def createDataFrameFromSQL(query: String): DataFrame =
    sqlContext.read.format("jdbc").options(sqlConf(query)).load()

  override def postStop() {
    sc.stop()
  }
}
