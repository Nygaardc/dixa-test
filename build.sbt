name := "dixa-test"
version := "1.0"
scalaVersion := "2.12.10"

resolvers += Resolver.bintrayRepo("thesamet", "sbt-protoc")

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
  "org.scalatest" %% "scalatest" % "3.1.1" % "test ",
  "com.typesafe.akka" %% "akka-stream" % "2.6.3",
  "com.typesafe.akka" %% "akka-http"   % "10.1.11",
//  "com.typesafe.akka" %% "akka-http2-support" % "10.1.11",
  "com.typesafe.akka" %% "akka-discovery"   % "2.6.3"
)

enablePlugins(AkkaGrpcPlugin)
// ALPN agent
//enablePlugins(JavaAgent)
//javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"
