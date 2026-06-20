package com.test.selenium.support;

public class config {
    private config(){}

    public static String baseUrl(){
        return System.getProperty("baseUrl","http://localhost:5173").replaceAll("/$","");
    }

    public static String Catalog(){
        return baseUrl() + "/catalog";
    }
    public static boolean headless(){
        return Boolean.parseBoolean(System.getProperty("headless","falses"));
    }
}
