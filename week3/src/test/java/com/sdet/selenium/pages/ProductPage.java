package com.sdet.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class ProductPage extends BasePage{
    private static final By PRODUCT_NAME =By.cssSelector("[data-test= 'detail-name']");
    private static final By PRODUCT_PRICE =By.cssSelector("[data-test= 'detail-name']");
    private static final By ADDCART = By.cssSelector("[data-test = 'add-to-cart']");
    ProductPage(WebDriver driver)
    {
        super(driver);
    }
    public String name()
    {
        return text(PRODUCT_NAME);
    }
    public CartPage addToCart()
    {
        click(ADDCART);
        wait.until(ExpectedConditions.urlContains("/cart"));
        return new CartPage(driver);
    }

}
