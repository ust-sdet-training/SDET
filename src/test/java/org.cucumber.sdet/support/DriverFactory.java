package org.cucumber.sdet.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();

        prefs.put(
                "credentials_enable_service",
                false
        );

        prefs.put(
                "profile.password_manager_enabled",
                false
        );

        prefs.put(
                "profile.password_manager_leak_detection",
                false
        );

        options.setExperimentalOption(
                "prefs",
                prefs
        );

        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments(
                "--window-size="
                        + Config.windowWidth()
                        + ","
                        + Config.windowHeight()
        );

        return new ChromeDriver(options);
    }

    public static void quit(WebDriver driver) {

        if (driver != null) {
            driver.quit();
        }
    }
}