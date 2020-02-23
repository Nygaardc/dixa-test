package com.nygaardc.dixa.services

import com.nygaardc.dixa.thrift.PrimeNumberService
import com.twitter.util.Future

import scala.collection.mutable


class PrimeNumberGenerator extends PrimeNumberService.MethodPerEndpoint {

  override def get(n: Int): Future[List[Int]] = {
    val primes = getPrimes(n)
    Future.value(primes)
  }

  /* Code adapted from:
  https://medium.com/coding-with-clarity/functional-vs-iterative-prime-numbers-in-scala-7e22447146f0
   */
  def getPrimes(n: Int): List[Int] = {
    val indexOfPrimes = mutable.ArrayBuffer.fill((n + 1) / 2)(1)

    val end = Math.sqrt(n).toInt // optimization: early stopping
    for (i <- 3 to n by 2 if i <= end) { // generate odd numbers
      for (nonPrime <- i * i to n by 2 * i) { // identify multiples of i as non prime
        indexOfPrimes.update(nonPrime / 2, 0)
      }
    }

    ( for (i <- indexOfPrimes.indices if indexOfPrimes(i) == 1)
      yield 2 * i + 1
    ).tail.toList
  }

  /* Functional but slow approach */
  //  override def get(n: Int): Future[Stream[Int]] = {
  //    val primes = sieve(Stream.from(2))
  //    Future.value(
  //      primes.takeWhile(_ <= n)
  //    )
  //  }
  //
  //  def sieve(stream: Stream[Int]): Stream[Int] =
  //    stream.head #:: sieve(stream.tail filter(_ % stream.head != 0))

}