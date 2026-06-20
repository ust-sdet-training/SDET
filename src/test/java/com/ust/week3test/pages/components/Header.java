package com.ust.week3test.pages.components;

import com.ust.week3test.pages.components.Header;
import com.ust.week3test.pages.BasePage;
import com.ust.week3test.pages.CartPage;
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

    public CartPage openCart(){
        click(CART_ICON);
        return new CartPage(driver);
    }
}
