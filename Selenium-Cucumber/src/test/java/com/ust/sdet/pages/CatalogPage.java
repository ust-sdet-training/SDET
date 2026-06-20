package com.ust.sdet.pages;

import com.ust.sdet.pages.components.ProductCard;
import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CatalogPage extends BasePage{
    private static final By SEARCH_INPUT = By.id("search-products");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By PRODUCT_CARD = By.className("product-card");
    private static final By SORT = By.id("sort-products");
    private static final By EMPTY_SEARCH = By.cssSelector("[data-test='empty-search']");
    private static final By FIRST_CLICK = By.cssSelector("[data-test='product-card'] a");

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage open() {
        driver.get(Config.catalogUrl());
        visible(SEARCH_INPUT);
        visible(RESULT_COUNT);
        return this;
    }

    public CatalogPage searchFor(String query) {
        String previousResultCount = text(RESULT_COUNT);
        type(SEARCH_INPUT, query, Keys.ENTER);
        wait.until((ignored) -> {
            String currentResultCount = text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products")
                    && !currentResultCount.equals(previousResultCount);
        });
        return this;
    }

    public CatalogPage searchFor(String query, String expectedResultCount) {
        searchFor(query);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedResultCount));
        return this;
    }

    public String emptySearchMessage() {
        return text(EMPTY_SEARCH);
    }

    public ProductPage openFirstProduct() {
        click(FIRST_CLICK);
        return new ProductPage(driver);
    }
}

