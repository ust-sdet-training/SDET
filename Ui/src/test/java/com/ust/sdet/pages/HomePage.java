package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    private final By searchBox = By.className("desktop-searchBar");
    public SearchResultsPage search(String product) {
        driver.findElement(searchBox)
                .sendKeys(product, Keys.ENTER);
        return new SearchResultsPage(driver);
    }
}