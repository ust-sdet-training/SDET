package com.selenium_ui.support;

public final class Config {

    private Config() {}

    public static String baseUrl() {
        return System.getProperty("BaseURL", "https://www.easemytrip.com").replaceAll("/$", "");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false")
        );
    }
}