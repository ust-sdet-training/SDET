package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderConfirmPage extends BasePage {

    private static final By CONFIRMATION_MESSAGE =
            By.cssSelector(".confirmation-panel");
    private static final By CONFIRMATION_TEXT =
            By.xpath("//h2[contains(text(),'Order Confirmed')]");

    public OrderConfirmPage(WebDriver driver) {
        super(driver);
    }
}