package com.nygaardc.dixa.services.finagle

import com.nygaardc.dixa.thrift.{BadInput, PrimeNumberService}
import com.twitter.finagle.http.service.HttpResponseClassifier
import com.twitter.finagle.thriftmux.service.ThriftMuxResponseClassifier
import com.twitter.finagle.{Http, ThriftMux}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.finch.syntax.get
import io.finch.{BadRequest, Endpoint, Ok, path}
import io.iteratee.Enumerator

object ProxyService extends TwitterServer {

  val port = flag[Int]("port", 8081, "port to serve REST api on")

  def main(): Unit = {
    val client = ThriftMux.client
      .withResponseClassifier(ThriftMuxResponseClassifier.ThriftExceptionsAsFailures)
      .build[PrimeNumberService.MethodPerEndpoint](":8080", "proxy-client")


    val api: Endpoint[Enumerator[Future, Int]] = get("prime" :: path[Int] ) { n: Int =>
      client
        .get(n)
        .map { primes => Ok(Enumerator.enumIterable[Future, Int](primes)) }
    } handle {
        case e: BadInput => BadRequest(e)
    }


    val server = Http.server
      .withStreaming(enabled = true)
      .withLabel("rest-api")
      .withResponseClassifier(HttpResponseClassifier.ServerErrorsAsFailures)
      .serve(s":${port()}", api.toService)

    closeOnExit(server)
    Await.ready(server)
  }
}
