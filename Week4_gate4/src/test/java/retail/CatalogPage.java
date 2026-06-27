package com.ust.sdet.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CatalogPage {

    private final SelenideElement search =
            $("#search-products");

    private final ElementsCollection products =
            $$("[data-test='product-card']");

    public CatalogPage searchFor(String product) {

        search.shouldBe(visible)
                .setValue(product)
                .pressEnter();

        return this;
    }

    public ElementsCollection results() {
        return products;
    }

    public CatalogPage openFirstProduct() {

        products.first()
                .shouldBe(visible)
                .$("a.button.primary")
                .click();

        return this;
    }
}