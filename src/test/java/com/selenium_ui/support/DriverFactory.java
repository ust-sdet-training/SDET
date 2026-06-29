package com.selenium_ui.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory(){}

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments(
                "--window-size=1440,900"
        );

        return new ChromeDriver(options);
    }
}