package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }


    public CartPage addToTheCart() {
        click(ADD_TO_CART);
        return new CartPage(driver);
    }

    public ProductPage clickCart(){
        click(ADD_TO_CART);
        return this;
    }
}