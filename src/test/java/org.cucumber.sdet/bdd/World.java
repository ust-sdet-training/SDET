package org.cucumber.sdet.bdd;

import org.cucumber.sdet.pages.CartPage;
import org.cucumber.sdet.pages.CatalogPage;
import org.cucumber.sdet.pages.CheckOutPage;
import org.cucumber.sdet.pages.ProductPage;
import org.cucumber.sdet.pages.components.Header;
import org.openqa.selenium.WebDriver;


public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckOutPage checkout;

    public Header header(){
        return new Header(driver);
    }
}
