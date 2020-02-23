name := "dixa-test"
version := "0.1"
scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.31.0",
  "com.github.finagle" %% "finch-circe" % "0.31.0",
  "com.github.finagle" %% "finch-refined" % "0.31.0",
  "io.circe" %% "circe-generic" % "0.12.3",
  "com.twitter" %% "finagle-thrift" % "20.1.0",
  "com.twitter" %% "finagle-thriftmux" % "20.1.0",
  "com.twitter" %% "finagle-http" % "20.1.0",
  "com.twitter" %% "util-core" % "20.1.0",
  "com.twitter" %% "scrooge-core" % "20.1.0",
  "com.twitter" %% "twitter-server" % "20.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test "
)
