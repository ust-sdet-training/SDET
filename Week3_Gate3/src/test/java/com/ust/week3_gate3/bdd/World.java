package com.ust.week3_gate3.bdd;

import org.openqa.selenium.WebDriver;

import com.ust.week3_gate3.pages.CartPage;
import com.ust.week3_gate3.pages.CatalogPage;
import com.ust.week3_gate3.pages.CheckoutPage;
import com.ust.week3_gate3.pages.ProductPage;
import com.ust.week3_gate3.pages.components.Header;

public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header() {
        return new Header(driver);
    }
}
