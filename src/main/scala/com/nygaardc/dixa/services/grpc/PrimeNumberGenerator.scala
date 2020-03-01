package com.nygaardc.dixa.services.grpc

class PrimeNumberGenerator {

  private def sieve(s: Stream[Int]): Stream[Int] = {
    s.head #:: sieve(s.tail filter (_ % s.head != 0))
  }

  def getPrimes: Stream[Int] = {
    sieve(Stream.from(2))
  }

}
