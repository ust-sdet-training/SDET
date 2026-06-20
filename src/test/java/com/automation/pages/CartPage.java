package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{
    private static final By CART_PAGE = By.cssSelector("[data-test = 'cart-page']");
    private static final By PRODUCTS = By.cssSelector("[data-test = 'cart-line']");
    private static final By CHECKOUT = By.cssSelector("[data-test = 'checkout-button']");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(CART_PAGE);
    }

    public int productCount() {
        return elements(PRODUCTS).size();
    }

    public CheckoutPage proceed() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }
}
