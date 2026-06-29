package com.ust.sdet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EaseMyTripTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver(new ChromeOptions());
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.easemytrip.com/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void homePageUrl() {
        assertEquals(driver.getCurrentUrl(), "https://www.easemytrip.com/");
    }

    @Test
    void clickHotels() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='hotels mainMenu'] a"))).click();
    }

    @Test
    void searchForHotels() throws Exception {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='hotels mainMenu'] a"))).click();
        WebElement location = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='hp_inputBox selectHtlCity']")));
        location.click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id='txtCity']")));
        input.sendKeys("Dubai");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='htlhp_btn']"))).click();
    }
}