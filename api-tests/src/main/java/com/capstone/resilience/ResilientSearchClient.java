package com.capstone.resilience;

import java.time.Duration;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class ResilientSearchClient {
  private final CircuitBreaker circuitBreaker;

  public ResilientSearchClient() {
    CircuitBreakerConfig config = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slowCallRateThreshold(50)
        .slowCallDurationThreshold(Duration.ofSeconds(2))
        .minimumNumberOfCalls(3)
        .slidingWindowSize(3)
        .permittedNumberOfCallsInHalfOpenState(2)
        .waitDurationInOpenState(Duration.ofSeconds(1))
        .build();
    CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
    this.circuitBreaker = registry.circuitBreaker("tripstack-search");
  }

  public <T> T execute(Supplier<T> supplier) {
    return circuitBreaker.executeSupplier(supplier);
  }
}
