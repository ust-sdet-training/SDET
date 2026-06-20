package com.ust.sdet.pages.component;

import com.ust.sdet.pages.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {

    private static final By CART_LINK =
            By.cssSelector("[data-test='cart-icon']");

    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public CartPage openCart() {
        driver.findElement(CART_LINK).click();
        return new CartPage(driver);
    }

    public CartBadge cartBadge() {
        return new CartBadge(driver);
    }
}
