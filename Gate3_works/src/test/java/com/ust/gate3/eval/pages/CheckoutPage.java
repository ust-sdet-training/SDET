package com.ust.gate3.eval.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage{

    private static final By CHECKOUT_TITLE = By.id("checkout-title");
    private static final By PLACE_ORDER = By.cssSelector("[data-test='place-order']");
    private static final By CONFIRM_ORDER = By.cssSelector("[data-test='order-confirmation']");


    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    public CheckoutPage placeOrder(){
        visible(CHECKOUT_TITLE);
        click(PLACE_ORDER);
        visible(CONFIRM_ORDER);
        return this;
    }

    public boolean isCheckoutPage(){
        return visible(CHECKOUT_TITLE).isDisplayed();
    }

    public String confirmationText(){
        return text(CONFIRM_ORDER);
    }


}
