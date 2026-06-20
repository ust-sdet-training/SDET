package com.ust.sdet.pages;

import com.ust.sdet.pages.components.ProductCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CatalogPage extends BasePage{
    private static final By SEARCH_FIELD = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_ELEMENT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");


    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage open(){
        makeconfiguration();
        visible(SEARCH_FIELD);
        visible(RESULT_ELEMENT);
        return this;
    }

    public CatalogPage searchFor(String query){
        visible(SEARCH_FIELD);
        String productCount=text(RESULT_ELEMENT);
        type(SEARCH_FIELD,query);
        spinner(RESULT_ELEMENT,productCount);
        return this;
    }

    public List<ProductCard> cards(){
        return visibileall(CARDS).stream()
                .map(ProductCard::new)
                .toList();
    }

    public List<String> titles(){
        return cards().stream()
                .map(ProductCard::title)
                .toList();
    }

    public ProductPage OpenFirstProduct(){
        click(PRODUCT_LINK);
        return new ProductPage(driver);
    }
}
