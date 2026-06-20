package com.week03_gate3_muhammedali.bdd;

import org.openqa.selenium.WebDriver;

import com.week03_gate3_muhammedali.pages.CartPage;
import com.week03_gate3_muhammedali.pages.CatalogPage;
import com.week03_gate3_muhammedali.pages.CheckoutPage;
import com.week03_gate3_muhammedali.pages.ProductPage;
import com.week03_gate3_muhammedali.pages.components.Header;

public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public CheckoutPage checkout;
    public ProductPage product;
    public CartPage cart;

    public Header header(){
        return new Header(driver);
    }
}
