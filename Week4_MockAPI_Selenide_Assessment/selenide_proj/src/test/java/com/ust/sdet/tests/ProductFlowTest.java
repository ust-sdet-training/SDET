package com.ust.sdet.tests;
import com.ust.sdet.pages.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.open;
import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.ProductPage;
import static com.codeborne.selenide.Selenide.page;

public class ProductFlowTest {

    @Test
    @DisplayName("Product should be displayed and ")
    void productFlow() {

        open("http://localhost:5173/catalog");

        CatalogPage cat= page(CatalogPage.class);

        ProductPage product = cat.searchFor("Headphones")
                .firstProduct();


        product.Title().shouldBe(visible);
        product.Title().shouldHave(text("Noise Canceling Headphones"));
        product.description().shouldHave(text("Wireless over-ear headphones with long battery life and commute-ready ANC."));
        product.stock().shouldHave(text("7 in stock"));

    }
}
