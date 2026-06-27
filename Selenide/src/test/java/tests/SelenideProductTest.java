package tests;

import config.SelenideConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.*;

class SelenideProductTest {

    @BeforeAll
    static void setup() {
        SelenideConfig.apply();
    }

    @Test
    @DisplayName("Verify product title contains running-shoes")
    void verifyProductTitle() {
        new ProductPage().openPage("running-shoes").verifyTitle("Running Shoes");
    }

    @Test
    @DisplayName("Verify product running-shoes has stocks")
    void verifyProductStock() {
        new ProductPage().openPage("running-shoes").verifyInStock("IN STOCK");
    }

    @Test
    @DisplayName("Verify product running-shoes can add to cart")
    void verifyAddToCartBtn() {
        new ProductPage().openPage("running-shoes").verifyAddToCart();
    }

    @Test
    @DisplayName("Verify CartBadge is one after product running-shoes is added")
    void verifyUpdateCartBadge() {
        new ProductPage().openPage("running-shoes").addToCart().verifyCartBadge("1");
    }
}