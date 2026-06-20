package com.ust.sdet.support;

public class Config {

    public static  String baseUrl()
    {
        return TestEnvironment.required("BaseURL").replaceAll("/$","");
    }
    public static String catalogUrl()
    {
        return baseUrl()+"/catalog";
    }
    public static boolean headless()
    {
        return Boolean.parseBoolean(TestEnvironment.required("HEADLESS"));
    }
}
