package org.cucumber.sdet.pages;

import org.cucumber.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.cucumber.sdet.pages.components.ProductCard;


import java.util.List;

public class CatalogPage extends BasePage {

    private static final By SEARCH=By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT=By.cssSelector("[data-test='catalog-result-count']");
    private static final By EMPTY_SEARCH=By.cssSelector("[data-test='empty-search']");
    private static final By CARDS=By.cssSelector("[data-test='product-card']");
    private static final By FIRST_LINK=By.cssSelector("[data-test='product-card'] a");
    private static final By SORT=By.cssSelector("[data-test='sort-select'] ");
    private static final By CATEGORY_SORT=By.cssSelector("[data-test='category-filter']");
    private static final By PRODUCT_CARD = By.cssSelector("[data-test='product-card']");


    public CatalogPage(WebDriver driver){
        super(driver);
    }

    public CatalogPage open(){
        driver.get(Config.catalogUrl());
        visible(SEARCH);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_COUNT));
        return this;
    }

    public CatalogPage searchFor(String query){
        String previousResultCount=header().Text(RESULT_COUNT);
        type(SEARCH,query, Keys.ENTER);
        wait.until((ignored)->{
            String currentResultCount=header().Text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products...") && !currentResultCount.equals(previousResultCount);
        });
        return this;
    }

    public CatalogPage searchFor(String text,
                                 String expectedCount) {

        WebElement searchInput =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                SEARCH
                        )
                );

        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.textToBe(
                        RESULT_COUNT,
                        expectedCount
                )
        );

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

    public CatalogPage sortBy(String visibleText) {
        WebElement card=visible(CARDS);
        new Select(visible(SORT)).selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.stalenessOf(card));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(CARDS,0));
        return this;
    }

    public CatalogPage sortCatagory(String visibleText){
        WebElement card=visible(CARDS);
        new Select(visible(CATEGORY_SORT)).selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.stalenessOf(card));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(CARDS,0));
        return this;
    }

    public String emptySearchMaessage(){
        return open().Text(EMPTY_SEARCH);
    }
    public ProductPage openFirstProduct(){
        click(FIRST_LINK);
        return new ProductPage(driver);
    }

}
