package com.ust.sdet.pages.Selenidepom;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelCatalogPage {

    private final String SEARCH = "[data-test='search-input']";
    private final String PRODUCT_CARD = "[data-test='product-card']";

    public SelCatalogPage searchFor(String q) {

        $(SEARCH)
                .shouldBe(visible)
                .setValue(q)
                .pressEnter();

        return this;
    }

    public ElementsCollection results() {
        return $$(PRODUCT_CARD);
    }
    public ElementsCollection productTitles() {
        return $$("[data-test='product-title']");
    }
}