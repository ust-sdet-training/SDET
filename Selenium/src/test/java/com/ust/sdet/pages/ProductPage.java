package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    private static final By LANDMARK = By.cssSelector("[data-test='detail-name']");
    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
        visible(LANDMARK);
    }

    public String productName() {
        return text(LANDMARK);
    }

    public ProductPage addToCart() {
        click(ADD_TO_CART);
        wait.until(d -> header().cartBadge().count() > 0);
        return this;
    }
}
