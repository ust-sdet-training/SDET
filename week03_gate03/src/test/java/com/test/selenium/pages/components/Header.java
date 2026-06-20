package com.test.selenium.pages.components;


import com.test.selenium.pages.basePage;
import com.test.selenium.pages.cartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends basePage {
    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");

    public Header(WebDriver driver){
        super(driver);
    }

    public cartBadge cartBadge(){
        return new cartBadge(wait);
    }

    public cartPage opencart(){
        click(CART_ICON);
        return new cartPage(driver);
    }
}

