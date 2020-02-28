package com.nygaardc.dixa.services.grpc

import akka.Done
import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import akka.stream.ActorMaterializer
import com.nygaardc.dixa.grpc.{PrimeNumberRequest, PrimeNumberService, PrimeNumberServiceClient}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object ProxyService {
  def main(args: Array[String]): Unit = {
    implicit val sys = ActorSystem("ProxyService")
    implicit val mat = ActorMaterializer()
    implicit val ec = sys.dispatcher

//    val settings = GrpcClientSettings.connectToServiceAt("localhost", 8080)
    val settings = GrpcClientSettings.fromConfig(PrimeNumberService.name)
    val client: PrimeNumberService = PrimeNumberServiceClient(settings)

    val responseStream = client.get(PrimeNumberRequest(17))
    val done: Future[Done] = responseStream.runForeach(resp => println(s"Receieved: ${resp.prime}"))
    done.onComplete {
      case Success(_) => println("stream done")
      case Failure(e) => println(s"Error: ${e}")
    }
//    sys.scheduler.scheduleAtFixedRate(1.second, 1.second) {    }
  }
}
