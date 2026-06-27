
package com.ust.sdet.selenide.tests;

import com.ust.sdet.selenide.pages.CatalogPage;
import com.ust.sdet.selenide.pages.LoginPage;
import com.ust.sdet.selenide.pages.ProductPage;
import com.ust.sdet.selenide.support.SelenideConfig;
import com.ust.sdet.selenide.support.TestEnvironment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.*;

public class ProductAvailabilityBadgeTest {

    @BeforeAll
    static void setup() {
        SelenideConfig.configure();
    }

    @Test
    void verifyAvailabilityBadgeAfterSearchingProduct() {

        LoginPage loginPage = page(LoginPage.class);
        loginPage.openSignPage()
                .login(TestEnvironment.required("EMAIL"), TestEnvironment.required("PASSWORD"));
        CatalogPage catalogPage = page(CatalogPage.class);
        catalogPage.open();

        catalogPage.searchFor("Laptop");

        ProductPage productPage = catalogPage.openFirstProduct();
        assertFalse(productPage.name().isEmpty());
        assertTrue(productPage.isAvailabilityBadgeDisplayed());


      //Verify Availability Badge Text
        assertEquals("In stock", productPage.availabilityBadge());
        assertTrue(productPage.price() > 0);
    }
}