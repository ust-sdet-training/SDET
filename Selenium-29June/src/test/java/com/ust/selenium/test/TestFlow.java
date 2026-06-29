package com.ust.selenium.test;

import com.ust.selenium.support.Config;
import com.ust.selenium.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestFlow {

    private static final By FROM = By.id("srcinput");
    private static final By TO = By.id("destinput");

    private static final By SEARCHBUTTON =
            By.cssSelector(
                    ".primaryButton___5380e6.searchButtonWrapper___48550e");
    private static final By FIRST_BUS =
            By.cssSelector(".rtcCardWrap___ed4236");

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {

        driver = DriverFactory.createChromeDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        driver.get(Config.baseUrl());
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testFlow() {

        driver.findElement(FROM)
                .sendKeys("Bangalore");

        driver.findElement(FROM)
                .sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        driver.findElement(TO)
                .sendKeys("Mysore");

        driver.findElement(TO)
                .sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        SEARCHBUTTON
                )
        ).click();
        wait.until(
                ExpectedConditions.elementToBeClickable(FIRST_BUS)
        ).click();
    }
}