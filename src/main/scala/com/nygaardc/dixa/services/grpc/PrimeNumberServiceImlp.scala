package com.nygaardc.dixa.services.grpc

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.nygaardc.dixa.grpc.{PrimeNumberRequest, PrimeNumberResponse, PrimeNumberService}

class PrimeNumberServiceImlp(implicit mat: Materializer) extends PrimeNumberService {
  import mat.executionContext

  override def get(in: PrimeNumberRequest): Source[PrimeNumberResponse, NotUsed] = {
    val primeNumberGenerator = new PrimeNumberGenerator
    val primes = primeNumberGenerator.getPrimes(in.n)
    Source(primes) map {i => PrimeNumberResponse(i)}
  }
}
