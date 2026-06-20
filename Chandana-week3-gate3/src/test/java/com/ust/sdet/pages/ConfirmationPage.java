package com.ust.sdet.pages;

import com.ust.sdet.pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage {

    private static final By ORDER_CONFIRMATION = By.cssSelector("[data-test='order-confirmation']");
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        visible(ORDER_CONFIRMATION);
    }

    public String confirmationMessage() {
        return text(ORDER_CONFIRMATION);
    }

    public Header header() {
        return new Header(driver);
    }
}