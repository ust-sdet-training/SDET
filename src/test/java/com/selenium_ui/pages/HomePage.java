package com.selenium_ui.pages;

import com.selenium_ui.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final By ONE_WAY = By.id("oway");
    private static final By FROM_CITY = By.id("FromSector_show");
    private static final By TO_CITY = By.id("Editbox13_show");
    private static final By DEPARTURE_DATE = By.className("active-date");
    private static final By SEARCH_BUTTON = By.id("search");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public SearchResultPage searchFlight(String from, String to) {
        driver.get(Config.baseUrl());
        click(ONE_WAY);
        type(FROM_CITY, from);
        driver.findElement(FROM_CITY).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        type(TO_CITY, to);
        driver.findElement(TO_CITY).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        click(DEPARTURE_DATE);
        driver.findElement(DEPARTURE_DATE).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        click(SEARCH_BUTTON);
        driver.findElement(SEARCH_BUTTON).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        return new SearchResultPage(driver);
    }
}