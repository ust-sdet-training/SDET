package com.ust.sdet.pages;

import com.ust.sdet.pages.components.ProductCard;
import com.ust.sdet.support.Config;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CatalogPage extends BasePage {

    private static final By SEARCH_INPUT =
            By.cssSelector("[data-test='search-input']");

    private static final By PRODUCT_CARD =
            By.cssSelector("[data-test='product-card']");

    private static final By PRODUCT_LINK =
            By.cssSelector("[data-test='product-card'] a");

    private static final By RESULT_COUNT =
            By.cssSelector("[data-test='catalog-result-count']");

    public String resultText() {

        return text(RESULT_COUNT);
    }


    public CatalogPage(WebDriver driver) {

        super(driver);
    }


    public CatalogPage open() {

        driver.get(Config.catalogUrl());

        return this;
    }


    public boolean isLoaded() {

        return visible(SEARCH_INPUT).isDisplayed();
    }


    public CatalogPage searchFor(String product) {

        WebElement searchBox = visible(SEARCH_INPUT);

        searchBox.clear();

        searchBox.sendKeys(product);

        searchBox.sendKeys(Keys.ENTER);

        return this;
    }


    public ProductPage openFirstProduct() {

        visibleElements(PRODUCT_LINK)
                .getFirst()
                .click();

        return new ProductPage(driver);
    }


    public List<ProductCard> cards() {

        return visibleElements(PRODUCT_CARD)
                .stream()
                .map(ProductCard::new)
                .toList();
    }


    public int productCount() {

        return elements(PRODUCT_CARD)
                .size();
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
}