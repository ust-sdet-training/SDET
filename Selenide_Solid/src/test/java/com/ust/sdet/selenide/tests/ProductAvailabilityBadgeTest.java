
package com.ust.sdet.selenide.tests;

import com.ust.sdet.selenide.pages.CatalogPage;
import com.ust.sdet.selenide.pages.LoginPage;
import com.ust.sdet.selenide.pages.ProductPage;
import com.ust.sdet.selenide.support.SelenideConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductAvailibilityBadgeTest {

    @BeforeAll
    static void setup() {
        SelenideConfig.configure();
    }

    @Test
    void verifyAvailabilityBadgeAfterSearchingProduct() {

        LoginPage loginPage = new LoginPage();
        loginPage.openSignPage()
                .login("customer@example.com", "Password@123");

        // Step 2 - Open Catalog
        CatalogPage catalogPage = new CatalogPage();
        catalogPage.open();

        // Step 3 - Search Product
        catalogPage.searchFor("Laptop");

        // Step 4 - Open Product Details
        ProductPage productPage = catalogPage.openFirstProduct();

        // Step 5 - Verify Product Name
        assertFalse(productPage.name().isEmpty());

        // Step 6 - Verify Availability Badge is Visible
        assertTrue(productPage.isAvailabilityBadgeDisplayed());

        // Step 7 - Verify Availability Badge Text
        assertEquals("In stock", productPage.availabilityBadge());

        // Step 8 - Verify Product Price
        assertTrue(productPage.price() > 0);
    }
}