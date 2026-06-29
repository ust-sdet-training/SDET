package com.ust.sdet.tests;

import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setup() {

        driver = DriverFactory.createChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(Config.baseUrl());
    }

    @AfterEach
    void tearDown() {

        if(driver != null){
            driver.quit();
        }
    }
}