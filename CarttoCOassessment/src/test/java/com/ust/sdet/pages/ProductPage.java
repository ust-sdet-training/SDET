package com.ust.sdet.pages;


import com.ust.sdet.pages.component.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public Header header() {
        return new Header(driver);
    }
}