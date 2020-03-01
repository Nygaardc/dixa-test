package com.nygaardc.dixa.services.grpc

import akka.NotUsed
import akka.grpc.GrpcServiceException
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.nygaardc.dixa.grpc.{PrimeNumberRequest, PrimeNumberResponse, PrimeNumberService}
import io.grpc.Status

class PrimeNumberServiceImlp(implicit mat: Materializer) extends PrimeNumberService {
  import mat.executionContext

  override def get(in: PrimeNumberRequest): Source[PrimeNumberResponse, NotUsed] = {
    if (in.n < 2) {
      return Source.failed(
        new GrpcServiceException(
          Status.INVALID_ARGUMENT.withDescription("numbers < 2 not allowed")
        )
      )
    }
    val primeNumberGenerator = new PrimeNumberGenerator
    val primes = primeNumberGenerator.getPrimes
    Source(primes)
      .takeWhile(_ <= in.n )
      .map(i => PrimeNumberResponse(i))
  }
}
