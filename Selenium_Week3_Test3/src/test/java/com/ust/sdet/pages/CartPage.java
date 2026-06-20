package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage{

    private static final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout-button']");
    private static final By LINES = By.cssSelector("[data-test='cart-line']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage proceed(){
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

    public int lineCount(){
        return elements(LINES).size();
    }



}
