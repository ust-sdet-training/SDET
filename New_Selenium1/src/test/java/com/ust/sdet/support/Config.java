package com.ust.sdet.support;

public class Config {
    private Config(){}

    public static String baseUrl(){
        return System.getProperty("baseUrl","https://www.amazon.in/")
                .replaceAll("/$","");
    }

    public static String login(){
        return baseUrl()+"/login";
    }



}
