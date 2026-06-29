package com.ust.sdet.tests;

import com.ust.sdet.pages.CartPage;
import com.ust.sdet.pages.HomePage;
import com.ust.sdet.pages.ProductListPage;
import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchToCartFlowTest {
    static WebDriver driver;
    @BeforeAll
    static void setUp(){
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @DisplayName("Catalog flow confirms the order from catalog to checkout")
    void catalogToConfirmOrder() {
        ProductListPage productListPage = new HomePage(driver).open().searchFor("shampoo");
        CartPage cartPage = productListPage.addProductToCart();
    }
}
