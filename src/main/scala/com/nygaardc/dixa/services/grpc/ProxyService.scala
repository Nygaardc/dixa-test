package com.nygaardc.dixa.services.grpc

import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.common.{CsvEntityStreamingSupport, EntityStreamingSupport}
import akka.http.scaladsl.marshalling.{Marshaller, Marshalling}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.util.ByteString
import com.nygaardc.dixa.grpc.{PrimeNumberRequest, PrimeNumberResponse, PrimeNumberService, PrimeNumberServiceClient}

import scala.io.StdIn

object ProxyService {
  def main(args: Array[String]): Unit = {
    implicit val sys = ActorSystem("ProxyService")
    implicit val ec = sys.dispatcher

    val settings = GrpcClientSettings.connectToServiceAt("localhost", 8080).withTls(false)
    val client: PrimeNumberService = PrimeNumberServiceClient(settings)

    implicit val csvFormat = Marshaller.strict[PrimeNumberResponse, ByteString] { resp =>
      Marshalling.WithFixedContentType(ContentTypes.`text/csv(UTF-8)`, () => {
        val prime = resp.prime
        ByteString(List(prime).mkString(","))
      })
    }

    implicit val streamingSupport: CsvEntityStreamingSupport = EntityStreamingSupport.csv()

    val route =
      pathPrefix("prime" / IntNumber) { n =>
        get {
          if (n < 2) complete(HttpResponse(StatusCodes.BadRequest, entity = "Error: number must not be < 2\n"))
          else complete { client.get(PrimeNumberRequest(n)) }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)
    println("Proxy server online at localhost:8081")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => sys.terminate())

  }
}
