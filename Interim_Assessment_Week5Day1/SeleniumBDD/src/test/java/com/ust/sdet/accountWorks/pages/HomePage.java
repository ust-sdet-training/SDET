package com.ust.sdet.accountWorks.pages;

import com.ust.sdet.accountWorks.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.picocontainer.visitors.VerifyingVisitor;

public class HomePage extends BasePage{
    private static final By SEARCH_BOX = By.id("twotabsearchtextbox");
    private static final By SEARCH_BUTTON = By.cssSelector("#nav-search-submit-button");
//    private static final By CARDS = By.cssSelector("[data-test='product-card']");
//    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");
//    private static final By SORT_BY = By.cssSelector("[data-test='sort-select']");
//    private static final By EMPTY_SEARCH = By.cssSelector("[data-test='empty-search']");


    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage openURL(){
        driver.get(Config.baseUrl());
        visible(SEARCH_BOX);
        return this;
    }

    public String isThisHome(){
        return driver.getCurrentUrl();
    }

    public HomePage searchFor(String query){
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BOX));
        type(SEARCH_BOX);
        return this;
    }
    public ProductListingPage clickSearch(){
        visible(SEARCH_BUTTON);
        click(SEARCH_BUTTON);
        return new ProductListingPage(driver);
    }
}

