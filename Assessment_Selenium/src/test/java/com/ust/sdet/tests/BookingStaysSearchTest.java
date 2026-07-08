package com.ust.sdet.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingStaysSearchTest {

    WebDriver driver;
    WebDriverWait wait;

    String url = "https://www.booking.com/";
    String destination = "London";
    void openHomePage() {
        driver.get(url);
        closeCookiePopup();

    }

    void closeCookiePopup() {
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("Dismiss sign-in info.")));
            closeButton.click();
        } catch (Exception ignored) {
        }
    }

    void enterDestinationValue() {
        WebElement destinationBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("ss")));
        destinationBox.clear();
        destinationBox.sendKeys(destination);
        destinationBox.sendKeys(Keys.ENTER);
    }


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void clean() {
        driver.quit();
    }

    @Test
    void openBookingCom() {
        openHomePage();
    }
    @Test
    void enterDestination() {
        openHomePage();
        WebElement destinationBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("ss")));
        destinationBox.clear();
        destinationBox.sendKeys(destination);
        assertTrue(destinationBox.getAttribute("value").contains(destination));
    }
    @Test
    void clickSearch() {
        openHomePage();
        enterDestinationValue();
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        searchButton.click();
        wait.until(ExpectedConditions.urlContains("searchresults"));
    }
}
