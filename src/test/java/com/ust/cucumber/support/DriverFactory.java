package com.ust.cucumber.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public final  class DriverFactory {

        private DriverFactory() {
        }

        public static WebDriver createChromeDriver() {
            ChromeOptions options = new ChromeOptions();

            if (Config.headless()) {
                options.addArguments("--headless=new");
            }

            options.addArguments("--window-size=1440,900");
            options.setExperimentalOption(
                    "prefs",
                    Map.of(
                            "credentials_enable_service", false,
                            "profile.password_manager_enabled", false
                    )
            );
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--incognito");

            return new ChromeDriver(options);
        }
    }
