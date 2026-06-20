package com.sdet.week3gate3.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addToCart() {
        click(ADD_TO_CART);
        return new ProductPage(driver);
    }
    public CartPage addToTheCart(){
        click(ADD_TO_CART);
        return new CartPage(driver);
    }
}