package com.ust.sdet.support;

import com.codeborne.selenide.Configuration;

public final class Config {
    private Config() {
    }

    public static void apply() {
        Configuration.baseUrl = "http://localhost:5173";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1440x900";
        Configuration.headless = false;
    }
}