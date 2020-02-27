package com.nygaardc.dixa.services

import com.nygaardc.dixa.services.finagle.PrimeNumberGenerator
import com.nygaardc.dixa.thrift.BadInput
import org.scalatest.funsuite.AsyncFunSuite
import com.twitter.util.{Return, Throw, Future => TwitterFuture}

import scala.concurrent.{Future, Promise}
import scala.concurrent.Future


class PrimeNumberGeneratorTest extends AsyncFunSuite {

  /* Convert from Twitter future to Scala future */
  def fromTwitter[A](twitterFuture: TwitterFuture[A]): Future[A] = {
    val promise = Promise[A]()
    twitterFuture respond {
      case Return(value) => promise.success(value)
      case Throw(exception) => promise.failure(exception)
    }
    promise.future
  }

  test("Generate list of primes") {
    val primeNumberGenerator = new PrimeNumberGenerator
    val future = fromTwitter(primeNumberGenerator.get(17))
    future map { numbers => assertResult (numbers) (Seq(2,3,5,7,11,13,17)) }
  }

  test("input is 2") {
    val primeNumberGenerator = new PrimeNumberGenerator
    val future = fromTwitter(primeNumberGenerator.get(2))
    future map { numbers => assertResult (numbers) (Seq(2)) }
  }

  test("input is zero") {
    val primeNumberGenerator = new PrimeNumberGenerator
    assertThrows[BadInput] { primeNumberGenerator.get(0) }
  }


  test("illegal input") {
    val primeNumberGenerator = new PrimeNumberGenerator
    assertThrows[BadInput] { primeNumberGenerator.get(-1) }
  }

}
