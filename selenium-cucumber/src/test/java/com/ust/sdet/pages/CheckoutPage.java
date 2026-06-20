package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By PLACE_ORDER = By.cssSelector("button.button.primary");
    private static final By CONFIRM = By.cssSelector(".confirmation-panel");

    public CheckoutPage(WebDriver driver){
        super(driver);
    }


    public CheckoutPage placeOrder(){
        click(PLACE_ORDER);
        visible(CONFIRM);
        return this;
    }


    public String confirmationText(){
        return text(CONFIRM);
    }

}