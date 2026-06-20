package com.ust.sdet.support;

public class Config {

    private Config(){}

    public static String baseUrl(){
        return TestEnvironment.required("BaseURL");
    }

    public static String CatalogUrl(){
        return baseUrl() +"/catalog";
    }

    public static boolean headless(){
        return Boolean.parseBoolean(TestEnvironment.optional("Headless","false"));
    }
}
