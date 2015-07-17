organization  := "connector"

version       := "0.1"

scalaVersion  := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.3.12"
  val sprayVersion = "1.3.3"
  val sparkVersion = "1.4.1"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayVersion,
    "io.spray"            %%  "spray-routing" % sprayVersion,
    "io.spray"            %%  "spray-json"    % "1.3.2",
    "io.spray"            %%  "spray-testkit" % sprayVersion  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaVersion,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaVersion   % "test",
    "org.apache.spark"    %%  "spark-sql"     % sparkVersion,
    "org.apache.spark"    %%  "spark-hive"    % sparkVersion,
    "org.apache.spark"    %%  "spark-core"    % sparkVersion,
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "mysql"        %   "mysql-connector-java" % "5.1.36"
  )
}

Revolver.settings
