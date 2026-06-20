package com.ust.sdet.bdd;

import com.ust.sdet.pages.*;
import com.ust.sdet.pages.components.Header;
import org.openqa.selenium.WebDriver;
public class World {

    public WebDriver driver;
    public LoginPage login;
    public HomePage homePage;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header() {
        return new Header(driver);
    }


}