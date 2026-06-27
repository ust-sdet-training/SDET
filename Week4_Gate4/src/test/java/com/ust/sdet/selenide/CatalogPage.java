package com.ust.sdet.selenide;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;

public class CatalogPage {
    private final SelenideElement searchInput = $("[data-test='search-input']");
    private final ElementsCollection cards = $$("[data-test='product-card']");

    public CatalogPage openPage() {
        open("http://localhost:5173/catalog");
        return this;
    }

    public CatalogPage searchFor(String keyword) {
        searchInput.shouldBe(visible).setValue(keyword).pressEnter();
        return this;
    }

    public ElementsCollection cards() {
        return cards;
    }

    public ProductPage click_product() {
        $("[data-test='product-card'] a").click();
        return new ProductPage();
    }
}
