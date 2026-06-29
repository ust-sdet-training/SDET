package com.example.Selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By searchBox =
            By.cssSelector(".search-input input, input[placeholder*='Search']");

    public void open(String url) {

        driver.get(url);
    }

    public void searchProduct(String productName) {

        WebElement search =
                wait.until(ExpectedConditions.elementToBeClickable(searchBox));

        search.click();
        search.clear();
        search.sendKeys(productName);

        new Actions(driver)
                .sendKeys(Keys.ENTER)
                .perform();

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("search"),
                    ExpectedConditions.urlContains(productName)
            ));
        } catch (TimeoutException e) {
            driver.get(searchUrl(productName));
        }
    }

    private String searchUrl(String productName) {
        String encodedProductName =
                URLEncoder.encode(productName, StandardCharsets.UTF_8);

        return "https://www.purplle.com/search?q=" + encodedProductName;
    }
}
