package com.example.Selenide.pages;

import com.example.Selenide.config.TestConfig;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.Selenide.pages.components.ProductCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;




public class CatalogPage {

    SelenideElement search = $("[data-test='search-input']");
    SelenideElement result = $("[data-test='catalog-result-count']");
    SelenideElement product_link = $("[data-test='product-card'] a");
    ElementsCollection productcard = $$("[data-test='product-card']");
    SelenideElement producttitle = $("[data-test='product-title']");


    public CatalogPage collect(){
        open(TestConfig.catalogUrl());
        return this;
    }

    public CatalogPage searchProduct(String value) {
        search.setValue(value).pressEnter();
        return this;
    }

    public CatalogPage verifyResult(String expected) {
        result.shouldHave(text(expected));
        productcard.shouldHave(sizeGreaterThan(0));
        return this;
    }

    public long cardcount(){
      return   productcard.stream().count();
    }

    public void verifycontents(){
        productcard.shouldHave(texts("Insulated Water Bottle","Yoga Mat","Resistance Bands Kit"));
    }

    public void verifynames(){
         productcard.filterBy(text("Yoga Mat"))
                .shouldHave(size(1));
    }

    public List<String> listOfProductsTitles(){
        return productcard
                .stream()
                .map(title -> new ProductCard(title).titles())
                .toList();
    }

    public List<Integer> listOfProductPrices(){
        return productcard
                .stream()
                .map(price -> new ProductCard(price).prices())
                .toList();
    }

    public ProductPage viewproduct(){
        product_link.click();
        return new ProductPage();
    }

}