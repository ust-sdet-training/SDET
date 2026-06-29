package com.ust.sdet.support;

public class Config {

    public static String baseUrl(){
        return System.getProperty("baseUrl","https://www.booking.com").replaceAll("/$","");
    }



    public static boolean headless(){
        return Boolean.parseBoolean(System.getProperty("headless","false"));
    }
}
