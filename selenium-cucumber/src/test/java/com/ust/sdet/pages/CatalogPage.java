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

public class CatalogPage extends BasePage {

    private static final By SEARCH_INPUT = By.cssSelector("[data-test='search-input']");
    private static final By PRODUCT_CARD = By.cssSelector("[data-test='product-card']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By SORT_SELECT = By.cssSelector("[data-test='sort-select']");


    public CatalogPage(WebDriver driver){
        super(driver);
    }

    public CatalogPage open(){
        driver.get(Config.catalogUrl());
        return this;
    }

    public CatalogPage searchFor(String text){
        WebElement search = visible(SEARCH_INPUT);
        search.clear();
        search.sendKeys(text);
        search.sendKeys(Keys.ENTER);
        return this;
    }
    public CatalogPage search(String text, String expectedCount){
        searchFor(text);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedCount));
        return this;
    }

    public ProductPage openFirstProduct(){
        visibleElements(PRODUCT_LINK).getFirst().click();
        return new ProductPage(driver);
    }


    public List<ProductCard> cards(){
        return elements(PRODUCT_CARD)
                .stream()
                .map(ProductCard::new)
                .toList();
    }


    public List<Integer> prices(){
        return cards()
                .stream()
                .map(ProductCard::price)
                .toList();
    }


    public CatalogPage sortBy(String label){
        WebElement old = visibleElements(PRODUCT_CARD).getFirst();
        new Select(visible(SORT_SELECT)).selectByVisibleText(label);
        wait.until(ExpectedConditions.stalenessOf(old));
        return this;
    }

}