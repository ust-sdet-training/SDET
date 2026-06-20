package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_LINE =
            By.cssSelector(".cart-items");

    private static final By CHECKOUT_BUTTON =
            By.cssSelector("button.button.primary");

    public CartPage(WebDriver driver) {

        super(driver);
    }

    public int lineCount() {

        return elements(CART_LINE).size();
    }

    public CheckoutPage checkout() {

        click(CHECKOUT_BUTTON);

        return new CheckoutPage(driver);
    }

    public boolean isLoaded() {

        return visible(CART_LINE).isDisplayed();
    }
}