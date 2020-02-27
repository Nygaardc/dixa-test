package com.nygaardc.dixa.services.finagle

import com.twitter.app.App
import com.twitter.finagle.ThriftMux
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
