package connector.model

trait Keyspaces {
  val keyspace = "connector"
}

trait Tables {
  val table = "tweets"
}

object CassandraBuilder extends Keyspaces with Tables {
/* TODO webapp shouldn't depend on spark configs
  def start = {
    CassandraConnector(Config.sparkConf).withSessionDo { session =>
      session.execute("CREATE KEYSPACE " + keyspace + " WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
    }
  }

  def createTable =  {
    CassandraConnector(Config.sparkConf).withSessionDo { session =>
      session.execute("CREATE TABLE " + keyspace + "." + table + " (id bigint PRIMARY KEY,user varchar, tweet text, createdat timestamp, location varchar )")
    }
  }

  def insert(user: String, tweet: String, location: String) =
    CassandraConnector(Config.sparkConf).withSessionDo { session =>
      session.execute("INSERT INTO "  + keyspace + "." + table + " (id, user, tweet, createdat, location) VALUES (" + user + "," + tweet + "," + new java.util.Date + "," + location +")")
    }
*/
}

