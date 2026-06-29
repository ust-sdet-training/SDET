package com.ust.sdet.pages;




import org.junit.jupiter.api.Test;

public class ProductfilterTest extends BaseTest {

    @Test
    void verifyProductFiltering() {

        HomePage home = new HomePage(driver);

        SearchResultsPage results =
                home.searchProduct("face wash");
//        results.filterByPrice();

    }
}