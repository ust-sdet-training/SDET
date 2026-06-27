package com.ust.sdet.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By PLACE_ORDER =
            By.cssSelector("[data-test='place-order']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmationPage placeOrder() {
        click(PLACE_ORDER);
        return new ConfirmationPage(driver);
    }
}