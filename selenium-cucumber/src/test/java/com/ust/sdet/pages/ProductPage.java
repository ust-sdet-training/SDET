package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By ADD_TO_CART = By.cssSelector("button.button.primary");

    public ProductPage(WebDriver driver){
        super(driver);
    }

    public CartPage addToCart(){
        click(ADD_TO_CART);
        return new CartPage(driver);
    }
}