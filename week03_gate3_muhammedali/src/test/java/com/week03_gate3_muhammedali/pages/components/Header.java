package com.week03_gate3_muhammedali.pages.components;

import javax.xml.catalog.Catalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.week03_gate3_muhammedali.pages.BasePage;
import com.week03_gate3_muhammedali.pages.CartPage;


public class Header extends BasePage{
    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");

    public Header(WebDriver driver){
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(wait);
    }

    public CartPage openCart(){
        click(CART_ICON);
        return new CartPage(driver);
    }
}