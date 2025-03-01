================
Circuit Breaker
================

=> It is one of the most famous design pattern in microservices.

=> It is used to implement fault tolerent systems.

=> Fault Tolerant systems also called as Resillence systems


Note: If main logic is failed to execute then we have to execute fallback logic.

=> In springboot, we can implement circuit breaker in 2 ways

				1) hystrix (outdated)

				2) Resillence4J (trending)

=> Circuit Breaker works based on 3 states

		1) CLOSED
		2) OPEN
		3) HALF_OPEN

================================
Circuit Breaker Implementation
================================

#### 1) Create Spring Boot project with below dependencies

		a) web-starter
		b) actuator
		c) aop
		d) resillence4J

		<dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-spring-boot3</artifactId>
			<version>2.0.2</version>
		</dependency>

#### 2) Create Rest Controller 

@RestController
public class DataRestController {

	@GetMapping("/data")
	@CircuitBreaker(fallbackMethod = "getDataFromDB", name = "ashokit")
	public String getData() {
		System.out.println("redis method called..");

		int i = 10 / 0;

		return "Redis Data sent to u r email";
	}

	public String getDataFromDB(Throwable t) {
		System.out.println("db method called..");
		return "DB Data sent to u r email";
	}

}


#### 3) Configure Circuit Breaker Properties

spring:
  application.name: resilience4j-demo

management:
  endpoints.web.exposure.include:
    - '*'
  endpoint.health.show-details: always
  health.circuitbreakers.enabled: true

resilience4j.circuitbreaker:
  configs:
    default:
      registerHealthIndicator: true
      slidingWindowSize: 10
      minimumNumberOfCalls: 5
      permittedNumberOfCallsInHalfOpenState: 3
      automaticTransitionFromOpenToHalfOpenEnabled: true
      waitDurationInOpenState: 100s
      failureRateThreshold: 50
      eventConsumerBufferSize: 10


#### 4) Test The application and monitor actuator health endpoint
