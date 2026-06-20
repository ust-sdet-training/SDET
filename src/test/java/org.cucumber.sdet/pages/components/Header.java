package org.cucumber.sdet.pages.components;

import org.cucumber.sdet.pages.BasePage;
import org.cucumber.sdet.pages.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {
    private static final By CART_ICON = By.cssSelector("a[href='/cart']");
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
