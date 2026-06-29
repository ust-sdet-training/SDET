package tests;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class SearchProductTest extends BaseTest {

    @Test
    public void verifyProductDetails() throws InterruptedException {

        HomePage home = new HomePage(driver);
        SearchResultsPage results = new SearchResultsPage(driver);
        home.searchProduct("Shoes");
        assertTrue(results.isSearchResultsDisplayed());
        results.clickFirstProduct();
        String parent = driver.getWindowHandle();

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
                break;
            }
        }
        Thread.sleep(3000);
        ProductPage product = new ProductPage(driver);
        assertTrue(product.isBrandDisplayed());
        assertTrue(product.isProductNameDisplayed());
        assertTrue(product.isPriceDisplayed());
        assertTrue(product.isImageDisplayed());
        assertTrue(product.getPageTitle().contains(product.getProductName()));
        assertTrue(product.getPrice() > 0);
    }
}