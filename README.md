![CD | Maven | Docker | AWS EC2](https://github.com/reirasdev/localidade-microservice/workflows/CD%20%7C%20Maven%20%7C%20Docker%20%7C%20AWS%20EC2/badge.svg?branch=master)

## Circuit Breaker implementation with Resilience4j
Software systems make remote calls to software running in different processes, usually on different machines across a network. One of the big differences between in-memory calls and remote calls is that remote calls can fail, or hang without a response until some timeout limit is reached. What's worse, if you have many callers on an unresponsive supplier, you can run out of critical resources leading to cascading failures across multiple systems.

#### Solution
The circuit breaker pattern is the solution to this problem. The basic idea behind the circuit breaker is very simple. You wrap a protected function call in a circuit breaker object, which monitors for failures. Once the failures reach a certain threshold, the circuit breaker trips, and all further calls to the circuit breaker return with an error or with some alternative service or default message, without the protected call being made at all. This will make sure system is responsive and threads are not waiting for an unresponsive call.

#### Different States of the Circuit Breaker
The circuit breaker has three distinct states: Closed, Open, and Half-Open:

**Closed** - When everything is normal, the circuit breaker remains in the closed state and all calls pass through to the services. When the number of failures exceeds a predetermined threshold, the breaker trips and it goes into the Open state.</br>
**Open** – The circuit breaker returns an error for calls without executing the function.</br>
**Half-Open** – After a timeout period, the circuit switches to a half-open state to test if the underlying problem still exists. If a single call fails in this half-open state, the breaker is once again tripped. If it succeeds, the circuit breaker resets back to the normal, closed state.</br>

#### Docker images for this application are available at:
https://hub.docker.com/r/reirasdev/localidade-microservice

#### Container with running system is available at:
http://ec2-18-228-59-179.sa-east-1.compute.amazonaws.com:8080/swagger-ui.html
