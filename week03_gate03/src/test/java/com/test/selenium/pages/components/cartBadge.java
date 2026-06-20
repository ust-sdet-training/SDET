package com.test.selenium.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cartBadge {
    private static final By COUNT =By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public cartBadge(WebDriverWait wait){
        this.wait = wait;
    }

    public int count(){
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(COUNT)).getText());
    }

    public void expectedCount(int expectedCount){
        wait.until(ExpectedConditions.textToBe(COUNT,String.valueOf(expectedCount)));
    }
}
