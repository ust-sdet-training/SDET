package com.external.exam.config;

public class Config {
    private Config()
    {

    }
    public static String baseurl()
    {
        return "https://www.myntra.com";
    }
    public static String catalog()
    {
        return baseurl()+"/catalog";
    }
    public static String login()
    {
        return baseurl()+"/login";
    }
    public static boolean headless()
    {
            return true;
    }
}
