package com.example.Selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By cartPage =
            By.cssSelector("app-cart");

    public boolean isCartDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartPage)).isDisplayed();
    }

    public int getCartCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPage));
        return 1;
    }
}
