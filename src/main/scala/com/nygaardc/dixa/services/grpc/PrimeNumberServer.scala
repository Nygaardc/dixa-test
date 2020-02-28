package com.nygaardc.dixa.services.grpc

import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, HttpConnectionContext}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.{ActorMaterializer, Materializer}
import com.nygaardc.dixa.grpc.PrimeNumberServiceHandler
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

object PrimeNumberServer {
  def main(args: Array[String]): Unit = {
//    val conf = ConfigFactory
//      .parseString("akka.http.server.preview.enable-http2 = on")
//      .withFallback(ConfigFactory.defaultApplication())
//    val system = ActorSystem("PrimeNumber", conf)
    val system = ActorSystem("PrimeNumber")
    new PrimeNumberServer(system).run()
  }
}

class PrimeNumberServer(system: ActorSystem) {
  def run(): Future[Http.ServerBinding] = {
    implicit val sys: ActorSystem = system
    implicit val mat: Materializer = ActorMaterializer()
    implicit val ec: ExecutionContext = sys.dispatcher

    val service: HttpRequest => Future[HttpResponse] = PrimeNumberServiceHandler(new PrimeNumberServiceImlp())

    val binding = Http().bindAndHandleAsync(
      service,
      interface = "localhost",
      port = 8080,
      connectionContext = HttpConnectionContext()
    )

    binding.foreach {binding =>
      println(s"gRPC server bound to: ${binding.localAddress}")
    }

    binding
  }

}
