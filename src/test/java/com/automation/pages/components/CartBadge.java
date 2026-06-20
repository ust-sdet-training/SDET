package com.automation.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartBadge {

    private static final By COUNT = By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait) {
        this.wait = wait;
    }

    public void expectCount(int expectedCount){
        wait.until(ExpectedConditions.textToBe(COUNT,String.valueOf(expectedCount)));
    }
}
