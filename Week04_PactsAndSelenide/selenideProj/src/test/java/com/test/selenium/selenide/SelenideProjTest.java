package com.test.selenium.selenide;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;

public class SelenideProjTest {
    @Test
    @DisplayName("Check availability of a product")
    void availabilityCheck() {
        catalogPage catalog = page(catalogPage.class).openPage().searchFor("headphone");

        productPage product = catalog.click_product();

        assertTrue(product.availabilityBadge().getText().contains("In stock"));
    }
}