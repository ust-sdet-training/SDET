package com.sdet.week3gate3.Pages;

import com.sdet.week3gate3.Pages.Components.ProductCard;
import com.sdet.week3gate3.Support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class CatalogPage extends BasePage{

    private static final By SEARCH_INPUT = By.cssSelector("[data-test='search-input']");
    private static final By PRODUCT_CARD = By.cssSelector("[data-test='product-card']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By SORT_SELECT = By.cssSelector("[data-test='sort-select']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");


    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage search(String text, String expectedCount) {

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));

        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedCount));

        return this;
    }

    public CatalogPage searchFor(String text) {

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));

        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        return this;
    }

    public ProductPage openFirstProduct() {

        elements(PRODUCT_LINK)
                .getFirst()
                .click();

        return new ProductPage(driver);
    }

    public CatalogPage open(){

        driver.get(Config.catalogUrl());
        return this;
    }

    public List<ProductCard> cards() {

        return driver.findElements(PRODUCT_CARD)
                .stream()
                .map(ProductCard::new)
                .toList();
    }

    public List<String> titles() {

        return cards()
                .stream()
                .map(ProductCard::title)
                .toList();
    }

    public List<Integer> prices() {

        return cards()
                .stream()
                .map(ProductCard::price)
                .toList();
    }

    public CatalogPage sortBy(String label) {

        WebElement oldFirst = visibleElements(PRODUCT_CARD).getFirst();
        new Select(visible(SORT_SELECT)).selectByVisibleText(label);
        wait.until(ExpectedConditions.stalenessOf(oldFirst));
        visibleElements(PRODUCT_CARD);

        return this;
    }


}

