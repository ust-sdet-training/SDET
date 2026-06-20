package com.automation.pages.components;

import com.automation.pages.BasePage;
import com.automation.pages.CartPage;
import com.automation.pages.CatalogPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {

    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");
    private static final By PRODUCTS_LINK = By.xpath("//a[text()='Products']");

    public Header(WebDriver driver) {
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(wait);
    }

    public CartPage openCart(){
        click(CART_ICON);
        return new CartPage(driver);
    }

    public CatalogPage clickProduct(){
        click(PRODUCTS_LINK);
        return new CatalogPage(driver);
    }
}
