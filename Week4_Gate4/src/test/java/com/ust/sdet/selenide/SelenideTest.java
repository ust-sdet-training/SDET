package com.ust.sdet.selenide;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;

public class SelenideTest {
    @Test
    @DisplayName("Check availability of a product")
    void availabilityCheck() {
        CatalogPage catalog = page(CatalogPage.class).openPage().searchFor("headphone");

        ProductPage product = catalog.click_product();

        assertTrue(product.availabilityBadge().getText().contains("In stock"));
    }
}
