import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}

object Example {

  val config = ConfigFactory.load("app")
  val list = List("some" -> 2, "hello" -> 3, "some" -> 1, "hello" -> 4, "bye" -> 4)

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster(config.getString("master-uri"))
      .setAppName(config.getString("app-name"))
    val sc = new SparkContext(sparkConf)

    val pairs = sc.parallelize(list)
    val reduced = pairs.reduceByKey(_ + _)

    reduced foreach println

    sc.stop()
  }

}