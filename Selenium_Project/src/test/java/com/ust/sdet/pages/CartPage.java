package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_ROW  = By.cssSelector(".cart-row");

    private static final By PROCEED = By.cssSelector("button.button.primary");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int lineCount() {
        return elements(CART_ROW).size();
    }

    public CheckoutPage proceed() {

        click(PROCEED);
        return new CheckoutPage(driver);
    }
}