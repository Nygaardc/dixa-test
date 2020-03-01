package com.nygaardc.dixa.services.grpc

import akka.actor.ActorSystem
import akka.grpc.GrpcServiceException
import akka.stream.scaladsl.Sink
import com.nygaardc.dixa.grpc.PrimeNumberRequest
import org.scalatest.funsuite.AsyncFunSuite

import scala.concurrent.Await
import scala.concurrent.duration._


class PrimeNumberServiceImlpTest extends AsyncFunSuite {
  implicit val sys: ActorSystem = ActorSystem("Test")

  test("get primes") {
    val service  = new PrimeNumberServiceImlp()
    val source = service.get(PrimeNumberRequest(17))
    val future = source.runWith(Sink.seq)
    val result = Await.result(future, 5.seconds)
    assertResult (result map (_.prime)) (Seq(2,3,5,7,11,13,17))
  }

  test("less than two") {
    val service  = new PrimeNumberServiceImlp()
    val source = service.get(PrimeNumberRequest(-1))
    val future = source.runWith(Sink.seq)
    assertThrows[GrpcServiceException] { Await.result(future, 5.seconds) }
  }

}
