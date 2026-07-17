package com.capstone.resilience;

public class FaultClassifier {
  public enum FailureMode {
    GATEWAY_DECLINE,
    GATEWAY_ERROR,
    GATEWAY_UNAVAILABLE,
    GATEWAY_TIMEOUT,
    BREAKER_OPEN,
    UNKNOWN
  }

  public FailureMode classify(int statusCode) {
    return switch (statusCode) {
      case 402 -> FailureMode.GATEWAY_DECLINE;
      case 502 -> FailureMode.GATEWAY_ERROR;
      case 503 -> FailureMode.BREAKER_OPEN;
      case 504 -> FailureMode.GATEWAY_TIMEOUT;
      default -> FailureMode.UNKNOWN;
    };
  }
}
