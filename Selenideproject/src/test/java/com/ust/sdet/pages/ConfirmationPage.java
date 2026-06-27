package com.ust.sdet.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage {

    private static final By MESSAGE =
            By.cssSelector("[data-test='order-confirmation']");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String confirmationText() {
        return text(MESSAGE);
    }
}