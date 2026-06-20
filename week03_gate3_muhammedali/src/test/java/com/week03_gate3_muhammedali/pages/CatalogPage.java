package com.week03_gate3_muhammedali.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.week03_gate3_muhammedali.pages.components.ProductCard;
import com.week03_gate3_muhammedali.support.Config;

public class CatalogPage extends BasePage{
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");


    public CatalogPage(WebDriver driver){
        super(driver);
    }

    public CatalogPage open(){
        driver.get(Config.catalogUrl());
        visible(SEARCH);
        visible(RESULT_COUNT);
        return this;
    }

    public CatalogPage searchFor(String query){
        String previousResultCount = text(RESULT_COUNT);
        type(SEARCH, query,Keys.ENTER);
        wait.until((ignored)->{
            String currentResultCount = text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products...") && !currentResultCount.equals(previousResultCount);
        });
        return this;
    }

    public CatalogPage searchFor(String query,String expectedResult){
        searchFor(query);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedResult));
        return this;
    }

    public List<ProductCard> cards(){
        return visibleElements(CARDS).stream().map(ProductCard::new).toList();
    }

    public List<String> titles(){
        return cards().stream().map(ProductCard::title).toList();
    }

    public List<Integer> prices(){
        return cards().stream().map(ProductCard::price).toList();
    }

    public ProductPage openFirstProduct(){
        click(FIRST_LINK);
        return new ProductPage(driver);
    }

}


