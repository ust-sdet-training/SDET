package com.ust.sdet.tests;

import com.ust.sdet.page.HomePage;
import com.ust.sdet.page.ProductPage;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Product_Search_Dynamic_ValidationTest {

    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;
    HomePage homePage;
    ProductPage productPage;

    @BeforeEach
    void setup() {
        driver=DriverFactory.createDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("As a user, I want to search for beauty products and validate product details")
    void purple()throws InterruptedException  {
        HomePage homePage=new HomePage(driver);
        ProductPage productPage= new ProductPage(driver);

        // STEP 1: Open Purplle homepage
        homePage.open();

        assertTrue(driver.getTitle().contains("Purplle"),
                "Title should contain Purplle");
        assertTrue(driver.getCurrentUrl().contains("purplle.com"),
                "URL should contain purplle.com");


//        1. Search → “face wash”
        productPage = homePage.clickHomeSearchBox("face wash");
        assertTrue(driver.getCurrentUrl().contains("search"), "URL should contain search");
        assertTrue(driver.getCurrentUrl().contains("face"), "URL should contain face");

        By productLocator = productPage.waitForProducts();
        assertNotNull(productLocator, "S3-Assert1: Product locator should not be null");
        assertTrue(productPage.getProductCount(productLocator) > 0, "Product count should be more than 0");

        //        2. Apply filter:
        int countBeforeBrand = productPage.getProductCount(productLocator);
        productPage.filterByBrand("Mamaearth");

        assertFalse(productPage.getProductCount(productLocator) > 0, "Products should exist after brand filter");

        //    * Price range
        int countBeforePrice = productPage.getProductCount(productLocator);
        productPage.filterByPrice();
        assertTrue(productPage.getProductCount(productLocator) <= countBeforePrice,
                " Price filter should not increase count");

//    * Price low → high
        productPage.sortByLowPrice();

        // 4. Capture product list
        List<String> products = productPage.getProductNames(productLocator);

        assertFalse(products.isEmpty(),
                "Product list should not be empty after sort");
        assertTrue(products.size() >= 1,
                "Should have at least 1 product after sort");
        for (int i = 0; i < Math.min(products.size(), 5); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }
}