package com.ust.sdet.selenide;


import com.ust.sdet.pages.Selenidepom.SelCatalogPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CsTest {

    @Test
    void searchProduct() {

        open("http://localhost:5173/catalog");

        SelCatalogPage page = page(SelCatalogPage.class);

        page.searchFor("headphones")
                .results()
                .shouldHave(sizeGreaterThan(0));
    }
    @Test
    void searchProducts() {

        open("http://localhost:5173/catalog");

        $$("[data-test='product-card']").shouldHave(size(3));

        $$("[data-test='product-title").shouldHave(texts("Running Shoes", "Travel Backpack", "Noise Canceling Headphones"));

        $$("[data-test='product-card")
                .filterBy(text("Running Shoes"))
                        .shouldHave(size(1));








    }
}
