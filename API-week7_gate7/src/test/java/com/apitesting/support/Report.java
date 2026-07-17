package com.apitesting.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Report {
    private static final Logger log = LoggerFactory.getLogger(Report.class);

    public static void step(String message) {
        log.info("[INFO] {}", message);
    }

    public static void pass(String message) {
        log.info("[PASS] {}", message);
    }

    public static void fail(String message) {
        log.error("[FAIL] {}", message);
    }

    public static void info(String key, Object value) {
        log.info("[INFO]  {} : {}", key, value);
    }
}