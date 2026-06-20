package com.ust.sdet.bdd;

import com.ust.sdet.pages.*;
import com.ust.sdet.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;
    public LoginPage login;

    public Header header(){
        return new Header(driver);
    }

}
