package com.ust.week3test.support;

public class Config {
    private Config(){}
    public static String baseUrl(){
        return TestEnv.optional("BASE_URL", "http://localhost:5173").replaceAll("/$", "");
    }
    public static String catalogUrl(){
        return baseUrl() + "/catalog";
    }
    public static boolean headless(){
        return Boolean.parseBoolean(TestEnv.optional("HEADLESS", "false"));
    }

}
