package com.ust.gate3.eval.support;

public class Config{
    private Config(){ }

    public static String baseUrl(){
        return EnvCheck.required("baseUrl").replaceAll("/$", "");
    }

    public static String catalogURL(){
        return baseUrl() + "/catalog";
    }

    public static boolean headless(){
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }


}
