package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {

    private final WebDriver driver;
    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");
    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        driver.findElement(CART_ICON).click();
    }
    private static final By PRODUCTS =
            By.linkText("Products");

    public void openProducts() {
        driver.findElement(PRODUCTS).click();
    }

    public CartBadge cartBadge() {
        return new CartBadge(driver);
    }
}