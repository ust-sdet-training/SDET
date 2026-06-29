package com.ust.sdet.pages;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {

        driver = DriverFactory.getDriver();

        driver.get("https://www.purplle.com/");
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}