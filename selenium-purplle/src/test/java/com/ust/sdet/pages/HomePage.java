package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import com.ust.sdet.support.Config;

import java.io.IOException;
import java.util.List;

public class HomePage extends BasePage {
    private static final WebElement SEARCH_CLICK = (WebElement) By.cssSelector("input[placeholder='What are you looking for?']");
    private static final By SEARCH = By.cssSelector("input[placeholder='What are you looking for?']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.baseUrl());
        visible(SEARCH);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_COUNT));
        return this;
    }

    public ProductListPage searchFor(String query) {
        new Actions(driver).click(SEARCH_CLICK).click().perform();
        click(SEARCH);
        return new ProductListPage(driver);
    }

    public HomePage searchFor(String query, String expectedResultCount) {
        searchFor(query);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedResultCount));
        return this;
    }
}
