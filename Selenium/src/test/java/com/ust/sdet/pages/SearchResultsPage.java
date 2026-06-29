package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage extends BasePage{

    private static final By RESULT_TITLE = By.cssSelector("[aria-live=\"assertive\"]");
    private static final By HOTELS = By.cssSelector("[data-testid=\"filters-group-label-content\"]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage verifyPage() {
        visible(RESULT_TITLE);
        return this;
    }

    public SearchResultsPage filterByProperty() {
        text(HOTELS);
//        click(hotel);
        return this;
    }
}
