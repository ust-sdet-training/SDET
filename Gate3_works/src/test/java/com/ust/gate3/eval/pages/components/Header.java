package com.ust.gate3.eval.pages.components;

import com.ust.gate3.eval.pages.BasePage;
import com.ust.gate3.eval.pages.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {

    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");

    public Header(WebDriver driver){
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(wait);
    }

    public CartPage opencart(){
        click(CART_ICON);
        return new CartPage(driver);
    }
}
