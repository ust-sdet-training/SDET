package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

public class HomePage extends BasePage{

    private static final By HERO_TEXT = By.cssSelector("[data-testid=\"herobanner-title1\"]");
    private static final By SEARCH_BUTTON = By.cssSelector("button[type='submit']");
    private static final By CLOSE = By.cssSelector("[aria-label=\"Dismiss sign-in info.\"]");
    private static final By DESTINATION = By.name("ss");
    private static final By DATE_PICKER = By.cssSelector("[data-testid='searchbox-dates-container']");
    private static final By NEXT_MONTH = By.cssSelector("button[aria-label='Next month']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.baseUrl());
        click(CLOSE);
        visible(HERO_TEXT);
        visible(SEARCH_BUTTON);
        return this;
    }

    public SearchResultsPage search(String place, String checkIn, String checkOut) {
        type(DESTINATION, place);
        driver.findElement(DESTINATION).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
//        click(DATE_PICKER);
        selectDate(LocalDate.parse(checkIn));
        selectDate(LocalDate.parse(checkOut));
        click(SEARCH_BUTTON);
        return new SearchResultsPage(driver);
    }

    private void selectDate(LocalDate date) {
        String dateValue = date.toString();

        while (driver.findElements(By.cssSelector("[data-date='" + dateValue + "']")).isEmpty()) {
            click(NEXT_MONTH);
        }
        click(By.cssSelector("[data-date='" + dateValue + "']"));
    }
}

