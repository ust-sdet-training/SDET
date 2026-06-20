package com.ust.cucumber.pages;

import com.ust.cucumber.pages.components.ProductCard;
import com.ust.cucumber.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CatalogPage extends  BasePage {
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By SORT = By.cssSelector("[data-test='sort-select']");
    private static final By EMPTY= By.cssSelector("[data-test='empty-search']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");


    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public  CatalogPage open(){
        driver.get(Config.catalogUrl());
        visible(SEARCH);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT));
        return this;
    }

    public CatalogPage searchFor( String query){
        String previousResultcount = text(RESULT);
        type(SEARCH,query);
        wait.until((i)->{
            String currentResultcount = text(RESULT);
            return !currentResultcount.equals("Searching products...")
                    && !currentResultcount.equals(previousResultcount);
        });
        return this;
    }




    public ProductPage openFirstProduct(){
        click(FIRST_LINK);
        return new ProductPage(driver);
    }


}



