package ust.gama.sdet.Gate4.selenide.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ust.gama.sdet.Gate4.selenide.helper.TestConfig;
import ust.gama.sdet.Gate4.selenide.pages.ProductPage;

public class AvailabiltyBadgeTest {
    @BeforeAll
    static void setup(){
        TestConfig.apply();
    }
    @Test
    @DisplayName("Test1: Running Shoes has Stock")
    void verifyProductStock() {
        new ProductPage().openProductPage("running-shoes").productInStock("IN STOCK");
    }

    @Test
    @DisplayName("Adding Running Shoes to Cart")
    void verifyAddToCartBtn() {
        new ProductPage().openProductPage("running-shoes").addProductToCart();
    }

    @Test
    @DisplayName("CartCount increases when product is added to cart")
    void verifyUpdateCartBadge() {
        new ProductPage().openProductPage("running-shoes").addProductToCart().checkCartCount("1");
    }
}
