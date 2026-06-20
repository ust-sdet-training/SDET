package com.ust.sdet.pages;

import com.ust.sdet.pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_PAGE = By.cssSelector("[data-test='cart-page']");
    private static final By CART_LINE = By.cssSelector("[data-test='cart-line']");
    private static final By PROCEED = By.cssSelector("[data-test='checkout-button']");
    public CartPage(WebDriver driver) {
        super(driver);
        visible(CART_PAGE);
    }

    public int lineCount() {
        return driver.findElements(CART_LINE).size();
    }

    public CheckoutPage proceed() {
        click(PROCEED);
        return new CheckoutPage(driver);
    }

    public Header header() {
        return new Header(driver);
    }
}