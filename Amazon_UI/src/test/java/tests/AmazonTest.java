package tests;

import Pages.HomePage;
import Pages.ProductPage;
import Pages.SearchResultPage;
import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AmazonTest extends BaseTest {

    @Test
    public void validateFilterSorting() {

        HomePage homePage = new HomePage(driver);
        SearchResultPage searchPage = new SearchResultPage(driver);
        ProductPage productPage = new ProductPage(driver);
        homePage.searchProduct("headphones");
        searchPage.selectBrand();
        searchPage.sortLowToHigh();
        List<Integer> actualPrices = searchPage.getPrices();

        System.out.println(actualPrices);
        int size = Math.min(actualPrices.size(), 10);

        for (int i = 0; i < size - 1; i++) {

            Assertions.assertTrue(
                    actualPrices.get(i) <= actualPrices.get(i + 1),
                    "Prices are not sorted");

        }

    }

}