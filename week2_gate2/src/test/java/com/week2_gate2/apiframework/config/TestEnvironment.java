package com.week2_gate2.apiframework.config;

public class TestEnvironment {
    public static String config(String value, String fallBack) {
        String keyBoardValue = System.getProperty(value);
        if(keyBoardValue != null && !keyBoardValue.isEmpty()) {
            return keyBoardValue;
        }
        String envValue = System.getenv(value);
        return envValue == null || envValue.isBlank() ? fallBack : envValue;
    }
}
