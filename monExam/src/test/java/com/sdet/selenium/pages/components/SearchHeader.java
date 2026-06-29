package com.sdet.selenium.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchHeader extends BaseComponent {
    private final By searchBox = By.cssSelector("#twotabsearchtextbox, input[name='field-keywords'], input#nav-search-keywords, input[name='k']");
    private final By searchButton = By.cssSelector("#nav-search-submit-button, input[type='submit'], input.nav-submit-input, button[type='submit']");

    public SearchHeader(WebDriver driver) {
        super(driver);
    }

    public void waitUntilVisible() {
        waitForVisible(searchBox);
    }

    public void searchFor(String keyword) {
        WebElement input = waitForVisible(searchBox);
        input.clear();
        input.sendKeys(keyword);
        waitForClickable(searchButton).click();
    }
}
