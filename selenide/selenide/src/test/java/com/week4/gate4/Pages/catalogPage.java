package com.week4.gate4.Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class catalogPage {

    private SelenideElement heading = $("#catalog-title");

    private SelenideElement searchBox = $("#search-products");
    private SelenideElement searchButton = $("[data-test='search-button']");

    private SelenideElement categoryDropdown = $("#category-filter");
    private SelenideElement sortDropdown = $("[data-test='sort-select']");

    private ElementsCollection productCards = $$(".product-card");
    private ElementsCollection productNames = $$("[data-test='product-title']");
    private ElementsCollection prices = $$("[data-test='product-price']");
    private ElementsCollection ratings = $$(By.xpath("//dt[text()='Rating']/following-sibling::dd"));
    private ElementsCollection stocks = $$(By.xpath("//dt[text()='Stock']/following-sibling::dd"));
    private ElementsCollection viewButtons = $$("a.button.primary");

    private SelenideElement totalProducts = $("[data-test='catalog-result-count']");


    public catalogPage openCatalogPage() {
        open("http://localhost:5173/catalog");
        return this;
    }

    public SelenideElement heading() {
        return heading;
    }

    public catalogPage searchFor(String product) {
        searchBox.shouldBe(visible)
                .setValue(product)
                .pressEnter();
        return this;
    }

    public catalogPage clickSearch() {
        searchButton.click();
        return this;
    }

    public catalogPage selectCategory(String category) {
        categoryDropdown.selectOption(category);
        return this;
    }

    public catalogPage sortBy(String option) {
        sortDropdown.selectOption(option);
        return this;
    }

    public ElementsCollection results() {
        return productCards;
    }

    public ElementsCollection productNames() {
        return productNames;
    }

    public ElementsCollection prices() {
        return prices;
    }

    public ElementsCollection ratings() {
        return ratings;
    }

    public ElementsCollection stocks() {
        return stocks;
    }


    public SelenideElement totalProducts() {
        return totalProducts;
    }

    public productPage clickFirstProduct() {

        viewButtons.first()
                .shouldBe(visible)
                .click();

        return page(productPage.class);
    }

}
