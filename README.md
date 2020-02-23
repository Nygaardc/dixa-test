# Dixa Backend Engineer Test
This README describes my implementation of a test given by Dixa 
as part of the hiring process for backend engineers.

## REST Endpoint
The REST endpoint is implemented using Finch as this library plays nicely with finagle.
One endpoint is defined at `/primes/<int>`. 
This endpoint parses the int from the URL path and invokes the `proxy-service` finagle client 
in order to pass the parameter to the `prime-number-server`.
The serving of the prime numbers over the endpoint is done in a streaming fashion (HTTP chunked transfer)
but it bears mentioning that the generation of then prime numbers takes place in a batch fashion. 
As such it is not until the prime numbers have been generated and delivered to the `proxy-service` 
that the numbers can be streamed. The transport between the finagle services communicate over Thrift which to my
best knowledge does not support streaming.
Wrong input (such as inputting a string instead of a number) is handled implicitly by the routing functionality
in Finch. In practise this means that a wrong input to the REST endpoint receives a HTTP statuscode of 404 (not found).

## Proxy Service
This client implements the Thrift defined service. It is used to call the method on the `prime-number-server`
which generates the primes (and returns them).

## Prime Number Server
This server also implements the Thrift defined service and creates a Finagle server which is used to deliver 
the generated prime numbers. The prime number generation functionality is implemented in an imperative fashion
because this proved to be faster then doing it in a functional (and recursive) way. 
