package com.ust.gate3.eval.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartBadge {

    private static final By CART_COUNT = By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait){
        this.wait=wait;
    }

    public int count(){
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(CART_COUNT)).getText());
    }

    public void expectedCount(int expectedCount){
        wait.until(ExpectedConditions.textToBe(CART_COUNT, String.valueOf(expectedCount)));
    }
}
