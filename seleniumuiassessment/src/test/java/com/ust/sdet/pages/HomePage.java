package com.ust.sdet.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private By searchBox =
            By.cssSelector("input[type='search']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage searchProduct(String product) {

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(searchBox));

        search.click();
        search.clear();
        search.sendKeys(product);

        System.out.println("Value = " + search.getAttribute("value"));

        search.sendKeys(Keys.ENTER);

        System.out.println("Current URL = " + driver.getCurrentUrl());

        return new SearchResultsPage(driver);
    }
}