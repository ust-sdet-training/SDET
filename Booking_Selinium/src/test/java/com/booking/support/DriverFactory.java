package com.booking.support;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        WebDriver wd = createDriver(browser);
        wd.manage().window().maximize();
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.set(wd);
    }

    private static WebDriver createDriver(String browser) {
        return switch (browser) {
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            case "chrome" -> new ChromeDriver();
            default -> new ChromeDriver();
        };
    }



    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver wd = driver.get();
        if (wd != null) {
            wd.quit();
            
        }
    }
}
