package com.shopkart.Config;


import com.codeborne.selenide.Configuration;

public class AppConfig {

    public static void apply(){
        Configuration.baseUrl ="http://localhost:8080";
        Configuration.browser = "Chrome";
        Configuration.browserSize ="1440x900";
        Configuration.headless =false;
        Configuration.timeout=5000;
        Configuration.screenshots =true;

        Configuration.reportsFolder = "test-output/screenshots";
    }
    public static String homeUrl(){return Configuration.baseUrl;}

}

