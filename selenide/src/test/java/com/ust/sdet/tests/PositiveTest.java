package com.ust.sdet.tests;

import com.ust.sdet.pages.CartPage;
import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.ProductPage;
import com.ust.sdet.support.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositiveTest {
    @BeforeEach
    void setUp() {
        Config.apply();
    }

    @Test
    @DisplayName("Availability of product")
    void checkAvailabilityOfProduct() {
        CatalogPage catalog = new CatalogPage();
        catalog.open().searchFor("headphones")
                .titles().shouldHave(sizeGreaterThan(0));

        ProductPage productPage = catalog.openItem();
        assertTrue(productPage.name().toLowerCase().contains("headphones"));
        assertTrue(productPage.availability().toLowerCase().contains("in stock"));
    }

    @Test
    @DisplayName("Search for headphones and add it to cart")
    void searchAndOpenProductAndAddToCartTest() {
        CatalogPage catalog = new CatalogPage();
        catalog.open().searchFor("headphones")
                .titles().shouldHave(sizeGreaterThan(0));

        ProductPage productPage = catalog.openItem();
        assertTrue(productPage.name().toLowerCase().contains("headphones"));
        CartPage cartPage = productPage.addToCart();
        $("[data-test='cart-count']").shouldHave(text("1"));
    }
}