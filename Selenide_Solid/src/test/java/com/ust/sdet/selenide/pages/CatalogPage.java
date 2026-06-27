package com.ust.sdet.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.ust.sdet.selenide.pages.components.Header;
import com.ust.sdet.selenide.pages.components.ProductCard;
import com.ust.sdet.selenide.support.SelenideConfig;
import com.ust.sdet.selenide.support.SelenideConfig.*;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CatalogPage {

    private static final ElementsCollection productNames = $$("[data-test='product-title']");
    private static final ElementsCollection productCategory = $(".product-grid").$$(".product-card .eyebrow");
    private static final ElementsCollection rows = $$("[data-test='rows']");
    private final SelenideElement search = $("[data-test='search-input']");

    private final SelenideElement resultCount = $("[data-test='catalog-result-count']");
    private final SelenideElement emptySearch = $("[data-test='empty-search']");
    private final ElementsCollection cards = $$("[data-test='product-card']");
    private final SelenideElement firstProduct = $("[data-test='product-card'] a");
    private final SelenideElement sort = $("[data-test='sort-select']");

    public ElementsCollection results(){
        return productNames;
    }

    public ElementsCollection categories() {
        return productCategory;
    }

    public CatalogPage openItem(){
        $$(".product-card .button.primary").first().click();
        return this;
    }


    public CatalogPage() {
    }

    public CatalogPage open() {
        Selenide.open(SelenideConfig.catalogUrl());
        search.shouldBe(visible);
        resultCount.shouldBe(visible);
        return this;
    }

    public CatalogPage searchFor(String query) {

        search.clear();
        search.setValue(query).pressEnter();

        resultCount.shouldNotHave(text("Searching products..."));

        return this;
    }

    public CatalogPage searchFor(String query, String expectedResultCount) {

        searchFor(query);

        resultCount.shouldHave(exactText(expectedResultCount));

        return this;
    }

    public CatalogPage sortBy(String visibleText) {

        sort.selectOption(visibleText);

        cards.shouldHave(sizeGreaterThan(0));

        return this;
    }

    public List<ProductCard> cards() {

        return cards.stream()
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

    public String emptySearchMessage() {

        return emptySearch.shouldBe(visible).text();
    }

    public ProductPage openFirstProduct() {

        firstProduct.click();
        return new ProductPage();
    }

    public Header header() {

        return new Header();
    }
}