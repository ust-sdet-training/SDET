package com.ust.sdet.tests;

import com.ust.sdet.pages.CatalogPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckAvailability {

    @Test
    public void CheckAvailabilityTest() {

        CatalogPage catalog = page(CatalogPage.class);

        catalog.openCatalogPage();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.searchFor("Travel");

        catalog.results()
                .shouldHave(sizeGreaterThan(0));

        catalog.results()
                .shouldHave(size(3));

        catalog.productNames()
                .shouldHave(texts(
                        "Travel Backpack",
                        "Resistance Bands Kit",
                        "Skin Care Travel Kit"
                ));

        catalog.results()
                .filterBy(text("TrailVault"))
                .shouldHave(size(1));

        catalog.clickProduct("TrailVault");

        catalog.ProductTitle().shouldHave(text("Backpack"));

        assertEquals("In stock",catalog.getAvailabilityBadge("Availability"));
    }
}
