package com.nygaardc.dixa.services.grpc

import org.scalatest.funsuite.AsyncFunSuite

class PrimeNumberGeneratorTest extends AsyncFunSuite {

  test("generate primes") {
    val pg = new PrimeNumberGenerator
    val primes = pg.getPrimes
    assertResult (primes takeWhile(_ <= 17)) (Stream(2,3,5,7,11,13,17))
  }

  test("empty primes") {
    val pg = new PrimeNumberGenerator
    val primes = pg.getPrimes
    assertResult (primes takeWhile(_ <= 0)) (Stream.empty)
    assertResult (primes takeWhile(_ <= 1)) (Stream.empty)
  }

  test("only one prime") {
    val pg = new PrimeNumberGenerator
    val primes = pg.getPrimes
    assertResult (primes takeWhile(_ <= 2)) (Stream(2))
  }

  test("negative") {
    val pg = new PrimeNumberGenerator
    val primes = pg.getPrimes
    assertResult (primes takeWhile(_ <= -1)) (Stream.empty)
  }

}
