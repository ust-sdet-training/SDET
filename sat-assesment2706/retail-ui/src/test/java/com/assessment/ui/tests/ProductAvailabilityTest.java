package com.assessment.ui.tests;

import com.assessment.ui.base.BaseTest;
import com.assessment.ui.pages.CatalogPage;
import com.assessment.ui.pages.LoginPage;
import com.assessment.ui.pages.ProductPage;
import org.junit.jupiter.api.Test;

public class ProductAvailabilityTest extends BaseTest {

    @Test
    void userCanOpenAProductAndVerifyAvailability() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.login(configReader.getUsername(), configReader.getPassword());
        loginPage.verifyLoginSucceeded();

        CatalogPage catalogPage = new CatalogPage().openCatalogPage();
        catalogPage.searchFor("shoe");
        catalogPage.verifySearchResultsDisplayed();

        ProductPage productPage = catalogPage.openFirstProduct();
        productPage.verifyProductPageOpened();
        productPage.verifyProductUrlContains("/product/");
        productPage.verifyTitleVisible();
        productPage.verifyAvailabilityLabelVisible();
        productPage.verifyAvailabilityValueEquals("In Stock");
        
    }
}
