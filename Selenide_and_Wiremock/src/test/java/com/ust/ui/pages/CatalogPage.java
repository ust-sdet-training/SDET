package com.ust.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class CatalogPage {
    private static final SelenideElement SEARCH = $("#search-products");
    private static final SelenideElement COUNT = $("[data-testid=\"catalog-result-count\"]");
    private static final ElementsCollection PRODUCTLIST = $$(".product-card");

    public CatalogPage openCatalog() {
        open("/catalog");
        $("#catalog-title").shouldHave(text("Product Catalog"));
        SEARCH.should(visible);
        SEARCH.shouldBe(enabled);
        COUNT.shouldHave(text("Showing 12 products"));
        return this;
    }

    public int search(String query) {
        SEARCH.type(query).pressEnter();
        SEARCH.shouldHave(value(query));
        int length = PRODUCTLIST.size();
        COUNT.shouldHave(text("Showing " + length + " product"));
        PRODUCTLIST.shouldHave(sizeGreaterThan(0));
        return length;
    }

    public ProductPage openProduct() {
        PRODUCTLIST.first()
                .$("a.button.primary")
                .shouldHave(text("View"))
                .click();
        return new ProductPage();
    }

    public CatalogPage quickViewHover() {
        PRODUCTLIST.first().hover();
        $(".quick-view")
                .shouldBe(visible)
                .shouldHave(exactText("Quick view ready"));
        return this;
    }
}
