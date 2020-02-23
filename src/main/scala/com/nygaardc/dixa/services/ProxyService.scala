package com.nygaardc.dixa.services

import com.nygaardc.dixa.thrift.PrimeNumberService
import com.twitter.finagle.http.service.HttpResponseClassifier
import com.twitter.finagle.thriftmux.service.ThriftMuxResponseClassifier
import com.twitter.finagle.{Http, Thrift, ThriftMux}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.finch._
import io.finch.syntax._
import io.finch.circe._
import io.finch.iteratee._
import io.iteratee.Enumerator
import io.circe.generic.auto._
import io.catbird.util._


object ProxyService extends TwitterServer {

  val port = flag[Int]("port", 8081, "port to serve web api on")

  def main(): Unit = {
    val client = ThriftMux.client
      .withResponseClassifier(ThriftMuxResponseClassifier.ThriftExceptionsAsFailures)
      .build[PrimeNumberService.MethodPerEndpoint](":8080", "proxy-client")


    val api: Endpoint[Enumerator[Future, Int]] = get("prime" :: path[Int] ) { n: Int =>
      client
        .get(n)
        .map { primes => Ok(Enumerator.enumIterable[Future, Int](primes)) }
    }

    val server = Http.server
      .withStreaming(enabled = true)
      .withLabel("web-api")
      .withResponseClassifier(HttpResponseClassifier.ServerErrorsAsFailures)
      .serve(s":${port()}", api.toService)

    closeOnExit(server)
    Await.ready(server)
  }
}
