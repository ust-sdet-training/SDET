package com.capstone.config;

public class EnvConfig {
  public static String require(String key) {
    String value = System.getenv(key);
    if (value == null || value.isBlank()) {
      throw new IllegalStateException("Missing required environment variable: " + key);
    }
    return value;
  }

  public static String mask(String value) {
    if (value == null) return "null";
    if (value.length() <= 4) return "*".repeat(value.length());
    return value.substring(0, 2) + "***" + value.substring(value.length() - 2);
  }
}
