package com.example.Selenium.tests;

import com.example.Selenium.config.Config;
import com.example.Selenium.pages.CartPage;
import com.example.Selenium.pages.HomePage;
import com.example.Selenium.pages.ProductPage;
import com.example.Selenium.pages.SearchResultsPage;
import com.example.Selenium.support.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurplleSearchTest extends BaseTest {

    @Test
    void searchAndAddProductToCartTest() {

        HomePage homePage = new HomePage(driver);
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);


        homePage.open(Config.baseUrl());


        homePage.searchProduct("lipstick");


        resultsPage.printAllProducts();


        resultsPage.clickFirstProduct();


        String productName = productPage.getProductName();
        String productPrice = productPage.getProductPrice();
        String productRating = productPage.getProductRating();


        System.out.println("Product Name : " + productName);
        System.out.println("Product Price : " + productPrice);
        System.out.println("Product Rating : " + productRating);


        Assertions.assertFalse(productName.isEmpty());
        Assertions.assertFalse(productPrice.isEmpty());


        productPage.clickAddToCart();


        Assertions.assertTrue(cartPage.isCartDisplayed());
        Assertions.assertTrue(cartPage.getCartCount() > 0);
    }
}
