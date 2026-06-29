package com.sdet.selenium.support;

import org.junit.jupiter.api.Assertions;

public final class AssertUtils {
    private AssertUtils() {}

    public static void assertNotBlank(String actual, String message) {
        Assertions.assertNotNull(actual, message);
        Assertions.assertFalse(actual.isBlank(), message);
    }

    public static void assertContainsIgnoreCase(String actual, String expected, String message) {
        Assertions.assertNotNull(actual, message);
        Assertions.assertTrue(actual.toLowerCase().contains(expected.toLowerCase()), message);
    }
}
