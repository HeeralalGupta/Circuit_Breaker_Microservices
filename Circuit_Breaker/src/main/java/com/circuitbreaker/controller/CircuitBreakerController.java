package com.circuitbreaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {
	
	@GetMapping("/data")
	@CircuitBreaker(fallbackMethod = "getDataFromDB", name = "testing")
	public String getDataFromRedis() {
		System.out.println("******* Redis method is executing ********");
//		int x = 10/0; (Failure condition to test circuit breaker states(CLOSE, OPEN, HALF_OPEN)
		return "This data is from redis method...";
	}
	
	public String getDataFromDB(Throwable t) {
		System.out.println("********* DB method is executing ********");
		return "This data is from DB method...";
	}
	
	
}
