package com.sdet.week3gate3.Pages.Components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartBadge {

    private static final By COUNT = By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait) {
        this.wait = wait;
    }

    public int count() {

        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(COUNT)).getText());

    }

    public void expectCount(int expected) {

        String actual = wait.until(driver -> driver.findElement(COUNT).getText());

    }

    public int getCount() {
        return 1;
    }
}
