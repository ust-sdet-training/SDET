package com.ust.sdet.Tests;

import com.codeborne.selenide.Configuration;
import com.ust.sdet.Pages.CatalogPage;
import com.ust.sdet.Pages.ProductPage;
import com.ust.sdet.utils.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SelenideTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = Config.baseUrl();
        Configuration.headless = Config.headless();

    }

    @Test
    @DisplayName("Checking availability")
    void availability() {

        new CatalogPage()
                .openCatalog()
                .searchProduct("trave")
                .openFirstProduct()
                .verifyProduct("In stock");
    }
}