package tests;

//import com.ust.sdet.pages.CartPage;
//import com.ust.sdet.pages.CatalogPage;
//import com.ust.sdet.pages.ProductPage;
//import com.ust.sdet.pages.components.ProductCard;
//import com.ust.sdet.support.Config;

import support.Config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.hu.De;
import support.DriverFactory;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    @DisplayName("Seat Selection")
    void seat_selection(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(SOURCE_LOCATION));
        driver.findElement(SOURCE_LOCATION).sendKeys("Trivandrum");
        wait.until(ExpectedConditions.visibilityOfElementLocated(DESTINATION_LOCATION));
        driver.findElement(DESTINATION_LOCATION).sendKeys("Bangalore");
        driver.findElement(DESTINATION_LOCATION).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
        driver.findElement(SEARCH_BUTTON).click();

//        driver.get("https://www.redbus.in/bus-tickets/thiruvananthapuram-to-bangalore?fromCityName=Trivandrum&fromCityId=71425&srcCountry=IND&fromCityType=CITY&toCityName=Bangalore&toCityId=122&destCountry=undefined&toCityType=CITY&onward=05-Jul-2026&doj=05-Jul-2026&ref=home");
//        driver.findElement(By.id("51938766")).click();


    }
}
