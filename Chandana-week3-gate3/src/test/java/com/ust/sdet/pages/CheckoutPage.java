package com.ust.sdet.pages;

import com.ust.sdet.pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By CHECKOUT_PAGE = By.cssSelector("[data-test='checkout-page']");
    private static final By PLACE_ORDER =
            By.cssSelector("[data-test='place-order']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        visible(PLACE_ORDER);
    }

    public ConfirmationPage placeOrder() {

        click(PLACE_ORDER);

        return new ConfirmationPage(driver);
    }

    public Header header() {
        return new Header(driver);
    }
}