package org.cucumber.sdet.pages;

import org.cucumber.sdet.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutPage extends BasePage {

    private static final By PLACE_ORDER =
            By.cssSelector("button.button.primary");

    private static final By CONFIRMATION_TEXT =
            By.cssSelector(".confirmation-panel");


    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    public CheckOutPage placeOrder() {
        click(PLACE_ORDER);
        return this;
    }

    public String confirmationText() {
        return header().Text(CONFIRMATION_TEXT);
    }
}