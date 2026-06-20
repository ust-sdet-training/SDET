package com.sdet.selenium.bdd;
import com.sdet.selenium.pages.*;
import com.sdet.selenium.pages.components.Header;
import org.openqa.selenium.WebDriver;


public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage  checkout;

    public Header header()
    {
        return new Header(driver);
    }

}
