package com.week4selenide.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.week4selenide.flow.SearchFlow;
import com.week4selenide.support.Config;
import static com.week4selenide.fixture.testProduct.*;

public class SelenideTest {
    
    @BeforeEach
    void setup(){
        Config.apply();
    }

    @Test
    public void checkProductBadge(){
        new SearchFlow().openCatalogPage().searchProduct(productName).openFirstProduct().abilityBadgeVisible();
    }
}
