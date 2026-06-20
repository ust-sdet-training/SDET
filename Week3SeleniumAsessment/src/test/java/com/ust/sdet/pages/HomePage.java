package com.ust.sdet.pages;

import com.ust.sdet.driver.DriverFactory;
import com.ust.sdet.support.Config;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;



import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = DriverFactory.createChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.homeUrl());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Home page loads")
    void homePageLoads() {
        assertTrue(driver.getPageSource().contains("SDET Retail Automation Lab"));
    }

    @Test
    @DisplayName("Sign in button opens login page")
    void signInOpensLogin() {
        WebElement signIn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(.,'Sign in')]")
                )
        );

        signIn.click();

        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}