package com.week4selenide.support;

import com.codeborne.selenide.Configuration;

public class Config {
    public static void apply() {
        Configuration.baseUrl = baseUrl();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1440x900";
        Configuration.headless = false;
    }

    public static String baseUrl(){
        return TestEnvironment.optional("baseUrl","http://localhost:5173/").replaceAll("/$", "");
    }

    public static String catalogUrl(){
        return "/catalog";
    }
}