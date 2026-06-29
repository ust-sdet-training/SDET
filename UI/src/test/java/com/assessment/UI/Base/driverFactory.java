package com.assessment.UI.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class driverFactory {

    private static WebDriver driver;

    public static void initializeBrowser() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.myntra.com/");
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitBrowser() {

        if (driver != null) {
            driver.quit();
        }
    }
}