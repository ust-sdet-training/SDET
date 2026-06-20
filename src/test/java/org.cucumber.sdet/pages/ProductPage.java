package org.cucumber.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private static final By ADD_TO_CART =
            By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addToCart() {
        click(ADD_TO_CART);
        return this;
    }

    public CartPage addToCartNew() {
        click(ADD_TO_CART);
        wait.until(ExpectedConditions.urlContains("/cart"));
        return new CartPage(driver);
    }
}