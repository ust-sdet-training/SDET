package com.ust.ui.tests;

import com.ust.ui.pages.CatalogPage;
import com.ust.ui.pages.ProductPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ui")
public class AvailabilityTest extends BaseTest{

    @Test
    public void availability() {
        CatalogPage catalogPage = new CatalogPage();
        catalogPage.openCatalog();
        catalogPage.search("sho");
        catalogPage.quickViewHover();
        ProductPage productPage = catalogPage.openProduct();
        productPage.verifyPage("In stock");
    }
}
