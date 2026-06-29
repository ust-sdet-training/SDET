package com.ust.sdet.pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver getDriver() {

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        return driver;
    }
}