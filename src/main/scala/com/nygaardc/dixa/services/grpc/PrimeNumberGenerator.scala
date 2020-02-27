package com.nygaardc.dixa.services.grpc

import scala.collection.mutable

class PrimeNumberGenerator {
  def getPrimes(n: Int): Stream[Int] = {
//    if (n < 2) throw new BadInput("Input cannot be < 2")
    val indexOfPrimes = mutable.ArrayBuffer.fill((n + 1) / 2)(1)

    val end = Math.sqrt(n).toInt // optimization: early stopping
    for (i <- 3 to n by 2 if i <= end) { // generate odd numbers
      for (nonPrime <- i * i to n by 2 * i) { // identify multiples of i as non prime
        indexOfPrimes.update(nonPrime / 2, 0)
      }
    }

    val primes = for (
      i <- indexOfPrimes.indices if indexOfPrimes(i) == 1)
      yield 2 * i + 1

    2 +: (primes tail).toStream
  }

}
