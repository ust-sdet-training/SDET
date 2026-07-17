package com.capstone.resilience;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class CircuitBreakerResilienceTest {
  @Test
  public void shouldCreateResilientClient() {
    ResilientSearchClient client = new ResilientSearchClient();
    String value = client.execute(() -> "ok");
    assertNotNull(value);
  }
}
