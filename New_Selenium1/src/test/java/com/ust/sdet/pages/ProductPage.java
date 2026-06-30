package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private final By addToCart =
            By.id("add-to-cart-button");

    private final By cart =
            By.id("nav-cart");

    public void addProduct() {
        driver.findElement(addToCart).click();
    }

    public void openCart() {
        driver.findElement(cart).click();
    }
}