package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCard {

    private final WebElement root;

    private static final By OPEN_PRODUCT =
            By.cssSelector("a[href*='/product/']");
    private static final By PRICE = By.cssSelector("[data-test='product-price']");
    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public ProductCard(WebElement root) {
        this.root = root;
    }
    public void open() {
        root.findElement(OPEN_PRODUCT).click();
    }
//    public String title() {
//        return root.findElement(TITLE).getText();
//    }

    public String price() {
        return root.findElement(PRICE).getText();
    }

    public void addToCart() {
        root.findElement(ADD_TO_CART).click();
    }
}