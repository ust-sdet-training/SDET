package com.week4_gate4.contractpact.Selenide.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;


import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideCatalogPage {
    static final String TITLE="[data-test='product-title']";
    static final String PRODUCT_CARDS="[data-test='product-card']";
    public static final ElementsCollection title=$$(TITLE);
    public  static final ElementsCollection rows=$$(PRODUCT_CARDS);
    public static final SelenideElement count_title=$("[data-test='catalog-result-count']");
    public static final SelenideElement first_card_viewbtn=$("a.button.primary");
    public static final SelenideElement detail_element=$("[data-test='detail-name']");
    public static final SelenideElement stock=$("[data-testid='availability-badge']");
    public static final SelenideElement btn=$("[data-test='add-to-cart']");

    public SelenideCatalogPage  searchFor(String query)
    {
        $("#search-products").shouldBe(visible)
                .setValue(query).pressEnter();
        $$(".product-card").shouldHave(sizeGreaterThan(0));
        return this;
    }
    public SelenideCatalogPage listpro(List r)
    {
        return this;
    }
    public SelenideCatalogPage  searchForanyProduct(String query)
    {
        $("#search-products").shouldBe(visible)
                .setValue(query).pressEnter();
        return this;
    }



}
