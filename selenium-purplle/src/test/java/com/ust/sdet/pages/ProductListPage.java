package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductListPage extends BasePage {
    private static final By TITLE = By.id("titleHeading");
    private static final By FIRST_LINK = By.id("lp-itm-0 a");
    private static final By SECOND_LINK = By.id("lp-itm-1 a");


    public ProductListPage(WebDriver driver) {
        super(driver);
        visible(TITLE);
    }

    public CartPage addProductToCart() {
        click(FIRST_LINK);
        click(SECOND_LINK);
        return new CartPage(driver);
    }
}
