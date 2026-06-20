package com.sdet.selenium.support;

public class Config {
    private Config()
    {

    }
    public static String baseURL()
    {
        return TestEnvironment.optional("BASE_URL", "http://localhost:5173")
                .replaceAll("/$", "");
    }
    public static boolean headless()
    {
        return Boolean.parseBoolean(TestEnvironment.optional("headless","false"));
    }
    public static  String catalogUrl()
    {
        return baseURL()+"/catalog";
    }


}
