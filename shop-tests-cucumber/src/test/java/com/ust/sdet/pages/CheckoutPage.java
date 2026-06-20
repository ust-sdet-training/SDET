package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage{
    private static final By PLACE_ORDER = By.cssSelector("[data-test = 'place-order']");
    private static final By CONFIRMATION = By.cssSelector("[data-test = 'order-confirmation']");
    private static final By CHECKOUT_TOTAL = By.cssSelector("[data-test = 'checkout-total']");

    protected CheckoutPage(WebDriver driver) {
        super(driver);
        visible(PLACE_ORDER);
    }

    public CheckoutPage placeOrder() {
        click(PLACE_ORDER);
        visible(CONFIRMATION);
        return this;
    }
    public String checkoutTotalText() {
        return text(CHECKOUT_TOTAL);
    }
    public String confirmationText() {
        return text(CONFIRMATION);
    }
}
