package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage{

    private static final By PLACE_THE_ORDER = By.cssSelector("[data-test='place-order']");
    private static final By CONFIRMATION_MESSAGE= By.cssSelector("[data-test='order-confirmation'] p:nth-of-type(2)");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        visible(PLACE_THE_ORDER);
    }

    public CheckoutPage placeOrder(){
        click(PLACE_THE_ORDER);
        visible(CONFIRMATION_MESSAGE);
        return this;
    }

    public String confirmationText(){
        return text(CONFIRMATION_MESSAGE);
    }


}
