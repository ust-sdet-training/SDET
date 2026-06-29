package com.ust.sdet.support;

public class Config {
    private Config(){}

    public static String baseUrl(){
        return TestEnvironment.required("baseUrl").replaceAll("/$","");
    }

    public static String SearchPage(){
        return baseUrl() + "/bus";
    }

    public static String Home(){
        return baseUrl() + "/";
    }


    public static boolean headless(){
        return Boolean.parseBoolean(TestEnvironment.required("headless"));
    }

}

