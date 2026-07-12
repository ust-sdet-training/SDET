package com.shopkart.support;

import com.codeborne.selenide.Selenide;
import com.shopkart.Config.AppConfig;
import com.shopkart.stepdefs.UIEndToEndTest;
import com.shopkart.stepdefs.UIProductSearchTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BaseUITest {

    @BeforeAll
    static void setup(){
        AppConfig.apply();
    }



    @Test
    void searchingAProductinShopkart(){
        UIProductSearchTest productSearchTest = new UIProductSearchTest();
        productSearchTest.searchingAProduct();
    }


    @Test
    void aHappyPath() {
        UIEndToEndTest ete = new UIEndToEndTest();
        ete.makeAEndToEndJourney();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}
