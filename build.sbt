import sbt.Keys._
import spray.revolver.RevolverPlugin.Revolver

name := "connector"

val commonSettings = Seq(
  organization := "connector",
  version := "0.1",
  scalaVersion := "2.11.7",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
)

lazy val root = (project in file(".")).settings(commonSettings: _*)

lazy val spark = (project in file("spark"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= {
      val sparkVersion = "1.4.1"
      Seq(
        "org.apache.spark"       %%  "spark-sql"                 % sparkVersion,
        "org.apache.spark"       %%  "spark-hive"                % sparkVersion,
        "org.apache.spark"       %%  "spark-core"                % sparkVersion,
        "org.apache.spark"       %%  "spark-streaming"           % sparkVersion,
        "org.apache.spark"       %%  "spark-streaming-twitter"   % sparkVersion
      )
    }
  )

lazy val webapp = (project in file("webapp"))
  .settings(commonSettings: _*)
  .settings(Revolver.settings)
  .settings(
    libraryDependencies ++= {
      val akkaVersion = "2.3.12"
      val sprayVersion = "1.3.3"
      Seq(
        "io.spray" %% "spray-can" % sprayVersion,
        "io.spray" %% "spray-routing" % sprayVersion,
        "io.spray" %% "spray-json" % "1.3.2",
        "io.spray" %% "spray-testkit" % sprayVersion % "test",
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
        "org.specs2" %% "specs2-core" % "2.3.11" % "test"
      )
    }
  )
