package org.selenium.sdet.bdd;

import org.openqa.selenium.WebDriver;
import org.selenium.sdet.pages.CartPage;
import org.selenium.sdet.pages.CatalogPage;
import org.selenium.sdet.pages.CheckoutPage;
import org.selenium.sdet.pages.ProductPage;
import org.selenium.sdet.pages.components.Header;
import io.cucumber.java.Scenario;

public class World {

    public WebDriver driver;
    public Scenario scenario;

    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header() {
        return new Header(driver);
    }
}
