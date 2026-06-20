package com.test.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends basePage {
    private static final By NAME = By.cssSelector("[data-test='detail-name']");
    private static final By ADD = By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
        visible(NAME);
    }

    public String name(){
        return text(NAME);
    }

    public cartPage addToCart(){
        click(ADD);
        urlchecking("/cart");
        return new cartPage(driver);
    }

}
