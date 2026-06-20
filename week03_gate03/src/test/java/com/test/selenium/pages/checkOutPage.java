package com.test.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class checkOutPage extends basePage{
    private static final By PLACE_ORDER = By.cssSelector("[data-test='place-order']");
    private static final By CONFIRMATION = By.cssSelector("[data-test='order-confirmation'] p:nth-of-type(2)");

    public checkOutPage(WebDriver driver) {
        super(driver);
        visible(PLACE_ORDER);
    }

    public checkOutPage placeOrder(){
        click(PLACE_ORDER);
        visible(CONFIRMATION);
        return this;
    }

    public String confirmationText(){
        return text(CONFIRMATION);
    }
}
