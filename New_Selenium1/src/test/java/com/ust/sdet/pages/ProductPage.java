package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    public static WebDriver driver;
    public ProductPage(WebDriver driver){
        super(driver);
    }
    public final By addToCart = By.id("add-to-cart-button");
    public final By cart = By.id("nav-cart-count-container");

    public void addProduct(){
        driver.findElement(addToCart).click();
    }

    public void openCart(){
        driver.findElement(cart).click();
    }

}
