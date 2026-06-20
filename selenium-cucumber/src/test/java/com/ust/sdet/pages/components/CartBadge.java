package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartBadge {

    private static final By COUNT = By.cssSelector("[data-test='cart-count']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartBadge(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public int count(){
        String value = wait.until(d-> d.findElement(COUNT).getText());
        return Integer.parseInt(value);
    }
}