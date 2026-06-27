package com.example.Selenide.tests;

import com.example.Selenide.config.TestConfig;
import com.example.Selenide.config.TestEnvironment;
import com.example.Selenide.pages.*;
import com.example.Selenide.pages.components.CartBadge;
import com.example.Selenide.pages.components.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SelenoideTest {

    @BeforeAll
    static void setup(){
        TestConfig.apply();
    }


    @Test
    @DisplayName("Module Test of Cart Verification")
    void checkAccessibility(){
        HomePage homePage = new HomePage()
                .gotohome();
        LoginPage loginPage = homePage.gotoLogin();
        homePage = loginPage.makeLogin(TestEnvironment.required("EMAIL"), TestEnvironment.required("PASSWORD"));

        CatalogPage catalogPage = homePage.goToCatalogAsLoggedUser()
                .searchProduct(TestEnvironment.required("PRODUCT_NAME"));

         ProductPage productPage = catalogPage.viewproduct();

           productPage.checkvisibility();
        }
}
