package com.ust.sdet.selenide.support;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class SelenideConfig {

    private SelenideConfig() {
    }

    public static void configure() {

        // Base URL
        Configuration.baseUrl = TestEnvironment.required("BASEURL");

        // Browser
        Configuration.browser = "chrome";

        // Headless
        Configuration.headless = Boolean.parseBoolean(TestEnvironment.required("HEADLESS"));


        // Browser size
        Configuration.browserSize = "1920x1080";

        // Timeout
        Configuration.timeout = 10000;

        // Browser lifecycle
        Configuration.holdBrowserOpen = false;
        Configuration.reopenBrowserOnFail = true;

        // Reports
        Configuration.screenshots = true;
        Configuration.savePageSource = true;


        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // Uncomment for Selenoid
        /*
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("name", "Retail Lab UI Tests");

        options.setCapability("selenoid:options", selenoidOptions);
        */

        Configuration.browserCapabilities = options;
    }


    public static String baseUrl() {
        return Configuration.baseUrl;
    }

    public static String loginUrl() {
        return baseUrl() + "/login";
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static String homeUrl() {
        return baseUrl() + "/home";
    }




}