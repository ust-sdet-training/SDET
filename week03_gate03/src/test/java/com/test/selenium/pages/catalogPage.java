package com.test.selenium.pages;


import com.test.selenium.pages.components.productCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class catalogPage extends basePage{
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By SORT = By.cssSelector("[data-test='sort-select']");
    private static final By EMPTY= By.cssSelector("[data-test='empty-search']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");


    public catalogPage(WebDriver driver) {
        super(driver);
    }

    public  catalogPage open(){
        makeconfiguration();
        visible(SEARCH);
        visible(RESULT);
        return this;
    }

    public catalogPage searchFor( String query){
        String previousResultcount = text(RESULT);
        System.out.println(previousResultcount);
        type(SEARCH,query);
        spinner(RESULT,previousResultcount);
        return this;
    }

    public catalogPage searchFor(String query,String expectedResultcount){
        searchFor(query);
        comparison(RESULT,expectedResultcount);
        return this;
    }

    public catalogPage sortby(String visbiletext){
        WebElement oldfirstcard=visible(CARDS);
        new Select(visible(SORT)).selectByVisibleText(visbiletext);
        staleness(oldfirstcard,CARDS);

        return this;
    }

    public List<productCard> cards(){
        return visibileall(CARDS).stream()
                .map(productCard::new)
                .toList();
    }

    public List<String> titles(){
        return cards().stream()
                .map(productCard::title)
                .toList();
    }

    public List<Integer> prices(){
        return cards().stream()
                .map(productCard::price)
                .toList();
    }

    public String emptySearchMessage(){
        return text(EMPTY);
    }

    public ProductPage openFirstProduct(){
        click(FIRST_LINK);
        return new ProductPage(driver);
    }


}
