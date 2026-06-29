package com.selenium.support;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    static Dotenv dotenv = Dotenv.load();
    private static String baseurl = dotenv.get("baseurl", "https://www.redbus.in/");

    public static String BASE_URL(){
        return baseurl;
    }
    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));
    }
}
