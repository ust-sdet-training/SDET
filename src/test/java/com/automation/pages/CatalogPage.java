package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CatalogPage extends BasePage {
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");


    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage isCatalogOpen() {
        visible(SEARCH);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_COUNT));
        return this;
    }

    public CatalogPage searchFor(String query) {
        String previousResultCount = text(RESULT_COUNT);
        type(SEARCH, ConfigReader.getConfigValue(query), Keys.ENTER);
        wait.until((ignored) -> {
            String currentResultCount = text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products...") && !currentResultCount.equals(previousResultCount);
        });
        return this;
    }

    public ProductPage viewProduct(){
        click(FIRST_LINK);
        return new ProductPage(driver);
    }
}
