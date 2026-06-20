package com.ust.cucumber.bdd;

import com.ust.cucumber.pages.CartPage;
import com.ust.cucumber.pages.CatalogPage;
import com.ust.cucumber.pages.CheckoutPage;
import com.ust.cucumber.pages.ProductPage;
import com.ust.cucumber.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class World {
        public WebDriver driver;
        public CatalogPage catalog;
        public ProductPage product;
        public CartPage cart;
        public CheckoutPage checkout;
        public Header header(){
            return new Header(driver);
        }
    }

