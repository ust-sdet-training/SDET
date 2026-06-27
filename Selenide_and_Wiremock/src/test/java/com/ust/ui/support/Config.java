package com.ust.ui.support;

public class Config {

    public static String baseUrl() {
        return System.getProperty("BASE_URL","http://localhost:5173");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless","true"));
    }
}