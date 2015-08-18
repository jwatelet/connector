package connector.core

import com.datastax.driver.core.{ProtocolOptions, Cluster}

trait ClusterBuilder {
  def cluster: Cluster
}

object CassandraCluster {
  lazy val cluster: Cluster =
    Cluster.builder().
      addContactPoints(Config.cassandraHost).
      withCompression(ProtocolOptions.Compression.SNAPPY).
      withPort(Config.cassandraPort)
      .build()
}