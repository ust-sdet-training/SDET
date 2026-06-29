package com.ust.sdet.tests;

import com.ust.sdet.pages.HomePage;
import com.ust.sdet.pages.SearchResultsPage;
import org.junit.jupiter.api.Test;

public class SearchTest extends BaseTest {
    @Test
    void searchForTshirts() {
        HomePage home = new HomePage(driver);
        home.search("T-Shirts");
    }
    @Test
    void verifyBrandFilter() {
        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.search("T-Shirt");
        results.searchBrand("AIGNER");
        results.selectBrand("AIGNER");
        results.waitForBrandFilterToApply("AIGNER");
    }
}