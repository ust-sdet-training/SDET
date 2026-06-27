package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CatalogPage {
    private static final By SEARCH = By.cssSelector("[data-test='search-input']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By EMPTY_SEARCH = By.cssSelector("[data-test='empty-search']");
    private static final By CARDS = By.cssSelector("[data-test='product-card']");
    private static final By FIRST_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By SORT = By.cssSelector("[data-test='sort-select']");

    private final SelenideElement SEARCH_SELENIDE = $("[data-test='search-input']");
    private final ElementsCollection CARDS_SELENIDE = $$("[data-test='product-card']");

    public CatalogPage open() {
        Selenide.open("/catalog");
        return this;
    }

    public CatalogPage searchFor(String query) {
        SEARCH_SELENIDE.setValue(query).pressEnter();
        return this;
    }

    public ElementsCollection titles() {
        return CARDS_SELENIDE;
    }

    public ProductPage openItem(){
        $$("[data-test='product-card'] a").first().click();
        return new ProductPage();
    }

}
