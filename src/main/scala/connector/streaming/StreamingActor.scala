package connector.streaming

import akka.actor.{Actor, ActorLogging}
import connector.streaming.job._
import org.apache.spark.streaming.StreamingContext

class StreamingActor(ssc: StreamingContext) extends Actor with ActorLogging {

  val jobs = initJobs()

  def receive = {
    case jobMsg: JobMessage => jobs(jobMsg).run(ssc, jobMsg.args)
    case msg => log.error(s"${self.path} received unknown message: $msg")
  }

  override def postStop() {
    ssc.stop()
  }

  def initJobs(): Map[JobMessage, StreamJob] = {
    Map {
      TwitterSampleJobMessage -> TwitterSample
    }
  }
}


