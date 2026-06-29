package com.ust.sdet.accountWorks.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ActualProductPage extends BasePage{
    private static final By DETAIL_NAME = By.cssSelector("[data-test='detail-name']");
    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public ActualProductPage(WebDriver driver){
        super(driver);
        visible(DETAIL_NAME);
    }

    public ActualProductPage productTitleContainsProductName(String prod_name){
        text(DETAIL_NAME).toLowerCase().contains(prod_name);
        return this;
    }
}
