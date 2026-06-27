package com.selenide.sdet.tests;

import com.selenide.sdet.pages.CatalogPage;
import com.selenide.sdet.pages.ProductPage;
import com.selenide.sdet.support.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {

    @BeforeEach
    void setUp(){
        Config.apply();
    }

    @Test
    @DisplayName("Assert the availability badge of product")
    void assertAvailabilityOfProduct(){
        CatalogPage catalog = page(CatalogPage.class);
        ProductPage product = page(ProductPage.class);
        catalog.open();
        catalog.searchFor("running").results().shouldHave(sizeGreaterThan(0));
        catalog.openItem();
        product.nameIsVisible().getAvailability().shouldHave(text("In stock"));

    }
}
