package com.ust.week3_gate3.pages;

import com.ust.week3_gate3.pages.components.ProductCard;
import com.ust.week3_gate3.support.Config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CatalogPage extends BasePage {
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By SORT = By.cssSelector("[data-test='sort-select']");

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage open() {
        driver.get(Config.catalogUrl());
        visible(SEARCH);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_COUNT));
        return this;
    }

    public CatalogPage searchFor(String query) {
        String previousResultScount = text(RESULT_COUNT);
        type(SEARCH, query, Keys.ENTER);
        wait.until((ignored) -> {
            String currentResultCount = text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products...") && !currentResultCount.equals(previousResultScount);
        });
        return this;
    }

    public CatalogPage searchFor(String query, String expectedResultCount) {
        searchFor(query);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedResultCount));
        return this;
    }

    public CatalogPage sortBy(String visibleText) {
        WebElement oldFirstCard = visible(CARDS);
        new Select(visible(SORT)).selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.stalenessOf(oldFirstCard));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(CARDS, 0));
        return this;
    }

    public ProductPage openFirstProduct() {
        click(FIRST_LINK);
        return new ProductPage(driver);
    }

    public List<ProductCard> cards() {
        return visibleElements(CARDS).stream().map(ProductCard::new).toList();
    }

    public List<String> titles() {
        return cards().stream().map(ProductCard::title).toList();
    }

    public List<Integer> prices() {
        return cards().stream().map(ProductCard::price).toList();
    }
}
