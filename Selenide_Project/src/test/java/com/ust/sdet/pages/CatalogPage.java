package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CatalogPage {

    private SelenideElement heading = $("#catalog-title");

    private SelenideElement searchBox = $("#search-products");
    private SelenideElement searchButton = $("[data-test='search-button']");

    private SelenideElement categoryDropdown = $("#category-filter");
    private SelenideElement sortDropdown = $("[data-test='sort-select']");

    private ElementsCollection productCards = $$(".product-card");
    private ElementsCollection viewButtons = $$("a.button.primary");

    private SelenideElement totalProducts = $("[data-test='catalog-result-count']");


    public CatalogPage openCatalogPage() {
        open("http://localhost:5173/catalog");
        return this;
    }

    public SelenideElement heading() {
        return heading;
    }

    public CatalogPage searchFor(String product) {
        searchBox.shouldBe(visible)
                .setValue(product);
        return this;
    }

    public CatalogPage clickSearch() {
        searchButton.click();
        return this;
    }

    public CatalogPage selectCategory(String category) {
        categoryDropdown.selectOption(category);
        return this;
    }

    public CatalogPage sortBy(String option) {
        sortDropdown.selectOption(option);
        return this;
    }

    public ProductPage clickFirstProduct() {

        viewButtons.first()
                .shouldBe(visible)
                .click();

        return page(ProductPage.class);
    }

}