package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartBadge {

    private static final By BADGE =
            By.cssSelector("[data-test='cart-count']");

    private final WebDriver driver;

    public CartBadge(WebDriver driver) {
        this.driver = driver;
    }

    public int count() {

        return Integer.parseInt(
                driver.findElement(BADGE).getText()
        );
    }
}