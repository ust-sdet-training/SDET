package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCard {

    private final WebElement root;

    private static final By TITLE = By.cssSelector(".product-card h2");

    private static final By PRICE = By.cssSelector(".price");

    public ProductCard(WebElement root){
        this.root = root;
    }

    public String title(){
        return root.findElement(TITLE).getText();
    }

    public int price(){
        return Integer.parseInt(root.findElement(PRICE).getText().replaceAll("[^0-9]",""));
    }

    public void open(){
        root.click();
    }
}