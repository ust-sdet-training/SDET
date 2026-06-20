package com.ust.sdet.bdd;

import com.ust.sdet.pages.CartPage;
import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.CheckoutPage;
import com.ust.sdet.pages.ProductPage;
import com.ust.sdet.components.Header;
import org.openqa.selenium.WebDriver;

public class World {

    public WebDriver driver;

    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;
    public String selectedProduct;
    public String cartTotal;
    public io.cucumber.java.Scenario scenario;

    public Header header() {
        return new Header(driver);
    }
}
