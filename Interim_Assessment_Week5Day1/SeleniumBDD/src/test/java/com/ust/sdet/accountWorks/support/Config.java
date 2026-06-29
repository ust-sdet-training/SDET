package com.ust.sdet.accountWorks.support;

public class Config {

    private Config(){
    }

    public static String baseUrl(){
        return System.getProperty("baseUrl", "https://www.amazon.in/").replaceAll("/$", "");
    }


    public static boolean headless(){
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static boolean headed(){
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}

