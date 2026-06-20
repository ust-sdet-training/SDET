package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private static final By LANDMARK = By.cssSelector("[data-test='cart-page']");
    private static final By LINES = By.cssSelector("[data-test='cart-line']");
    private static final By TOTAL = By.cssSelector("[data-test='cart-total']");
    private static final By CHECKOUT = By.cssSelector("[data-test='checkout-button']");
    private static final By EMPTY_MESSAGE = By.xpath("//*[@data-test='cart-page']//*[@role='status' and contains(.,'empty')]");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(LANDMARK);
        wait.until(d -> elements(By.cssSelector(".spinner")).isEmpty());
    }

    public int lineItemCount() {
        return elements(LINES).size();
    }

    public String total() {
        return text(TOTAL);
    }

    public boolean isEmpty() {
        return isDisplayed(EMPTY_MESSAGE) && lineItemCount() == 0;
    }

    public CheckoutPage checkout() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }
}
