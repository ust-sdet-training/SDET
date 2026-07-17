package com.tripstack.config;

import java.time.LocalDate;

public class ConfigManager {

    public static final String BASE_URL = getEnv("TRIPSTACK_BASE_URL", "https://tripstack.doomple.com/api");
    public static final String EMAIL    = getEnv("TRIPSTACK_EMAIL", "niaj@tripstack.test");
    public static final String PASSWORD = getEnv("TRIPSTACK_PASSWORD", "Password@123");
    public static final String EMP_ID   = getEnv("TRIPSTACK_EMP_ID", "1014");
    public static final int PERF_SAMPLE_SIZE = Integer.parseInt(getEnv("TRIPSTACK_PERF_SAMPLE_SIZE", "10"));
    public static final long PERF_THRESHOLD_MS = Long.parseLong(getEnv("TRIPSTACK_PERF_THRESHOLD_MS", "2000"));

    public static final String FROM_CITY = "IXC";
    public static final String TO_CITY   = "BLR";
    public static final int DATE_OFFSET_DAYS = 15;

    private static String getEnv(String key, String fallback) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? fallback : v;
    }

    public static String maskedEmail() {
        if (EMAIL == null || EMAIL.isBlank()) return "***";
        int at = EMAIL.indexOf('@');
        if (at <= 1) return "***" + EMAIL.substring(at);
        return EMAIL.charAt(0) + "***" + EMAIL.substring(at);
    }

    public static String travelDate() {
        return LocalDate.now().plusDays(DATE_OFFSET_DAYS).toString();
    }
}