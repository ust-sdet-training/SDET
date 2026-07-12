package com.shopkart.stepdefs;

import com.shopkart.data.secrets.Secrets;
import com.shopkart.support.BaseUITest;
import com.shopkart.ui.pages.CatalogPage;
import io.qameta.allure.Step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UIProductSearchTest extends BaseUITest {

    @Step
    public void searchingAProduct(){
        CatalogPage catalogPage = new CatalogPage();

        catalogPage.goToCatalog();

        catalogPage.searchTheProduct(Secrets.get("product"));

        assertTrue(catalogPage.VerifyTheProduct(Secrets.get("product")));
    }
}
