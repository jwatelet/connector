package connector.api

import akka.actor.Actor
import com.datastax.driver.core.{BoundStatement, Cluster, Row}
import connector.api.TweetReaderActor.CountAll
import connector.models.Tweet

trait Keyspaces {
  val keyspace = "connector"
}

trait Columns {
  val column = "tweets"
}

object Cassandra extends Keyspaces with Columns

object TweetReaderActor {
  case object CountAll
}


class TweetReaderActor(cluster: Cluster) extends Actor {
  val session = cluster.connect(Cassandra.keyspace)
  val countAll  = new BoundStatement(session.prepare("select count(*) from tweets;"))


  def buildTweet(row: Row): Tweet = {
    val id = row.getString("key").toInt
    val user = row.getString("user_user")
    val text = row.getString("text")
    val createdAt = row.getDate("createdat")
    Tweet(id, user, text, createdAt)
  }

  def receive: Receive = {
    case CountAll =>
      session.executeAsync(countAll)
  }
}


