package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_LINE = By.cssSelector("[data-test='cart-line']");
    private static final By TOTAL = By.cssSelector("[data-test='cart-total']");
    private static final By PROCEED = By.cssSelector("[data-test='checkout-button']");

    public CartPage(WebDriver driver){
        super(driver);
    }

    public int lineCount(){
        return driver.findElements(CART_LINE).size();
    }


    public String total(){
        return exists(TOTAL) ? text(TOTAL) : "Rs. 0";
    }

    public CheckoutPage proceed(){
        click(PROCEED);
        return new CheckoutPage(driver);
    }
}