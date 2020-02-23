package com.nygaardc.dixa.services

import com.twitter.finagle.{Thrift, ThriftMux}
import com.twitter.app.App
import com.twitter.finagle.thriftmux.service.ThriftMuxResponseClassifier
import com.twitter.util.Await


object PrimeNumberServer extends App  {
  def main(): Unit = {
    val server = ThriftMux.server
      .withResponseClassifier(ThriftMuxResponseClassifier.ThriftExceptionsAsFailures)
      .withLabel("prime-number-server")
      .serveIface(":8080", new PrimeNumberGenerator)
    closeOnExit(server)
    Await.ready(server)
  }
}
