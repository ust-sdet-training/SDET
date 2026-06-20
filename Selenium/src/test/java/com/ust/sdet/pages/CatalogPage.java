package com.ust.sdet.pages;

import com.ust.sdet.components.ProductCard;
import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CatalogPage extends BasePage {
    private static final By LANDMARK = By.cssSelector("[data-test='catalog-title']");
    private static final By SEARCH_INPUT = By.cssSelector("[data-test='search-input']");
    private static final By SEARCH_BUTTON = By.cssSelector("[data-test='search-button']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");

    public CatalogPage(WebDriver driver) {
        super(driver);
        visible(LANDMARK);
    }

    public static CatalogPage open(WebDriver driver) {
        driver.get(Config.catalogUrl());
        return new CatalogPage(driver);
    }

    public CatalogPage searchFor(String productName) {
        type(SEARCH_INPUT, productName);
        click(SEARCH_BUTTON);
        wait.until(d -> !text(RESULT_COUNT).equals("Searching products..."));
        return this;
    }

    public ProductCard productNamed(String productName) {
        return new ProductCard(driver, productName);
    }
}
