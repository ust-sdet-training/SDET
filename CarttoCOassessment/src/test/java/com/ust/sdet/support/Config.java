package com.ust.sdet.support;

public class Config {
    private Config(){

    }
    public static String baseUrl() {
        return TestEnvironment.optional(
                "BASE_URL",
                "http://localhost:5173"
        );
    }
    public static String catalogUrl(){
        return baseUrl() + "/catalog";
    }
    public static boolean headless() {
        return Boolean.parseBoolean(TestEnvironment.optional("HEADLESS", "false"));
    }
}
