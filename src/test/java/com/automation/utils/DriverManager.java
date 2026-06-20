package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class DriverManager {

    private DriverManager() {
    }

    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        if (ConfigReader.getConfigValue("headless").equals("true")) options.addArguments("---headless=new");

        options.addArguments("---window-size=1920,1080");

        return new ChromeDriver(options);
    }
}
