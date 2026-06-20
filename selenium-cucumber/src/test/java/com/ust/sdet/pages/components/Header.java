package com.ust.sdet.pages.components;

import com.ust.sdet.pages.BasePage;
import com.ust.sdet.pages.CartPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {

    private static final By CART = By.cssSelector("a[data-test='cart-icon']");

    public Header(WebDriver driver){
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(driver, wait);
    }

    public CartPage openCart(){
        click(CART);
        return new CartPage(driver);
    }
}