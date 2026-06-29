package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    private By filterButton =
            By.xpath("//button[contains(.,'Filter')]");

    public void filterByPrice() {
        click(filterButton);
    }
}