package com.week4.gate4.support;
import com.codeborne.selenide.Configuration;

public class config {
    public static void apply() {
        Configuration.baseUrl = "http://localhost:5173";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1440x900";
        Configuration.headless = false;
        Configuration.pageLoadTimeout = 30000;
        }

}
