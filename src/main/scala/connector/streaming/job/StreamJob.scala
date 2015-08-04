package connector.streaming.job

import org.apache.spark.streaming.StreamingContext

trait StreamJob {
  def run(ssc: StreamingContext, args: Seq[Any]): Unit = {
    ssc.stop()
    runJob(ssc, args)
    ssc.start()
  }
  protected def runJob(ssc: StreamingContext, args: Seq[Any])
}

trait JobMessage {
  def args: Seq[Any]
}