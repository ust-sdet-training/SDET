package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By PLACE_ORDER =
            By.cssSelector("button.button.primary");

    public CheckoutPage(WebDriver driver) {

        super(driver);
    }

    public OrderConfirmPage placeOrder() {

        click(PLACE_ORDER);

        return new OrderConfirmPage(driver);
    }
}