package com.ust.sdet.tests;

import com.ust.sdet.pages.*;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class cartManagementTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {

        driver = DriverFactory.getDriver();
        driver.get("https://www.amazon.in/");

    }

    @AfterEach
    public void tearDown() {

        driver.quit();

    }

    @Test

    public void verifyCartManagementFlow() {

        HomePage home = new HomePage(driver);
        SearchResults search = new SearchResults(driver);
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

//        home.open();

        home.searchProduct("USB Keyboard");

        search.openFirst();

        product.addProduct();

        driver.navigate().back();

        search.openSecond();

        product.addProduct();

        product.openCart();

        cart.verifyTwoProducts();

        cart.removeOneProduct();

        cart.verifyOneProduct();

        cart.refreshPage();

        cart.verifyOneProduct();
    }

}