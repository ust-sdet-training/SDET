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
    private static final By SEARCH_CLICK = By.cssSelector("input[placeholder='What are you looking for?']");
    private static final By SEARCH = By.className("search-pop");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.baseUrl());
        return this;
    }

    public ProductListPage searchFor(String query) {
        click(SEARCH_CLICK);
        visible(SEARCH);
        type(SEARCH, query, Keys.ENTER);
        return new ProductListPage(driver);
    }

}
