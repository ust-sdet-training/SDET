package com.capstone.resilience;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LivePaymentResilienceTest {
  @Disabled("Requires the live TripStack payment endpoint and a real proctor-controlled fault scenario")
  @Test
  public void shouldClassifyLivePaymentFaults() {
    FaultClassifier classifier = new FaultClassifier();
    FaultClassifier.FailureMode mode = classifier.classify(402);
    assertNotNull(mode);
  }
}
