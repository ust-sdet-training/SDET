package com.ust.gate3.eval.pages;

import com.ust.gate3.eval.pages.components.ProductCard;
import com.ust.gate3.eval.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CatalogPage extends BasePage{
    private static final By SEARCH_BOX = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By SORT_BY = By.cssSelector("[data-test='sort-select']");
    private static final By EMPTY_SEARCH = By.cssSelector("[data-test='empty-search']");


    public CatalogPage(WebDriver driver){
        super(driver);
    }

    public CatalogPage open(){
        driver.get(Config.catalogURL());
        visible(SEARCH_BOX);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_COUNT));
        return this;
    }

    public String isThisCatalogURL(){
        return driver.getCurrentUrl();
    }

    public CatalogPage searchFor(String query){
        String previousResultCount=text(RESULT_COUNT);
        type(SEARCH_BOX, query, Keys.ENTER);
        wait.until((ignored) -> {
            String currentResultCount=text(RESULT_COUNT);
            return !currentResultCount.equals("Searching products...")
                    && !currentResultCount.equals(previousResultCount);
        });
        return this;
    }

    public CatalogPage searchFor(String query, String expectedResultCount){
        searchFor(query);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, expectedResultCount));
        return this;
    }

    public CatalogPage sortBy(String visibleText){
        WebElement oldFirstProduct= visible(CARDS);
        new Select(visible(SORT_BY)).selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.stalenessOf(oldFirstProduct));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(CARDS, 0));
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

    public String emptySearch(){
        return text(EMPTY_SEARCH);
    }

    public ProductPage openFirstProduct(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_LINK));
        wait.until(ExpectedConditions.elementToBeClickable(FIRST_LINK));

        click(FIRST_LINK);
        return new ProductPage(driver);
    }
}
