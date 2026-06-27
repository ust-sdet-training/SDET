package com.ust.sdet.Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    private final SelenideElement title = $("#catalog-title");
    private final SelenideElement searchBox = $("#search-products");
    private final ElementsCollection products = $$(".product-card");

    public CatalogPage openCatalog() {
        open("/catalog");

        title.shouldHave(text("Product Catalog"));
        searchBox.shouldBe(visible);

        return this;
    }

    public CatalogPage searchProduct(String product) {
        searchBox.setValue(product).pressEnter();

        searchBox.shouldHave(value(product));
        products.shouldHave(sizeGreaterThan(0));

        return this;
    }

    public ProductPage openFirstProduct() {

        products.first().$("a.button.primary")
                .click();

        return new ProductPage();
    }
}