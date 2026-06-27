package config;

import com.codeborne.selenide.Configuration;

public class SelenideConfig {
    public static void apply() {
        Configuration.baseUrl = "http://localhost:5173";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1440x900";
        Configuration.headless = false;
    }
}
