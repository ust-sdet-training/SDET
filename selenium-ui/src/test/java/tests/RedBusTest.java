package tests;

import org.openqa.selenium.WebElement;
import support.Config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import support.DriverFactory;

import java.time.Duration;


class RedBusTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach

    void setUp() {
        driver = DriverFactory.createChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.baseUrl());
        Config.headed();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static final By SOURCE_LOCATION = By.id("srcinput");
    private static final By DESTINATION_LOCATION = By.id("destinput");
    private static final By SEARCH_BUTTON = By.xpath("//button[contains(.,'Search') or contains(.,'SEARCH')]");
//    private static final By Search = By.className("icon-search");
//    private static final By SEATS = By.cssSelector("[aria-label=\"View seats for Punchiry Travels and Holidays\"]");

    @Test
    @DisplayName("Seat Selection")
    void seat_selection(){
        WebElement source =
                wait.until(ExpectedConditions.elementToBeClickable(SOURCE_LOCATION));
        source.clear();
        source.sendKeys("Trivandrum");
        source.sendKeys(Keys.ARROW_DOWN);
        source.sendKeys(Keys.ENTER);
        WebElement destination =
                wait.until(ExpectedConditions.elementToBeClickable(DESTINATION_LOCATION));
        destination.clear();
        destination.sendKeys("Bangalore");
        destination.sendKeys(Keys.ARROW_DOWN);
        destination.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BUTTON));
        driver.findElement(SEARCH_BUTTON).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(SEATS));


    }
}