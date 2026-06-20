package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{
    private static final By CART_LINE = By.cssSelector(".cart-items");
    private static final By PROCEED = By.cssSelector("button.button.primary");
    private static final By TOTAL_PRICE = By.cssSelector("[data-test='cart-total']");
    private static final By REMOVE = By.cssSelector(".button.secondary");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int lineCount() {
        return elements(CART_LINE).size();
    }

    public double total() {
        return Integer.parseInt(driver.findElement(TOTAL_PRICE).getText().replaceAll("[^0-9]", ""));
    }

    public void removeItem() {
        click(REMOVE);
    }

    public CheckoutPage proceed() {
        click(PROCEED);
        return new CheckoutPage(driver);
    }
}
