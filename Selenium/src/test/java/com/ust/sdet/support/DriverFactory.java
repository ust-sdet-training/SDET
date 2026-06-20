package com.ust.sdet.support;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class DriverFactory {

    private DriverFactory() {}

    public static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);

        prefs.put("profile.password_manager_enabled", false);

        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);


        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1440, 900");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-debugging-pipe");
        options.addArguments("--user-data-dir=" + Path.of("target", "chrome-profiles", UUID.randomUUID().toString()).toAbsolutePath());

        return new ChromeDriver(options);
    }

}


