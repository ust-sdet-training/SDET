package com.sdet.week3gate3.BDD;

import com.sdet.week3gate3.Pages.*;
import com.sdet.week3gate3.Pages.Components.Header;
import org.openqa.selenium.WebDriver;

public class World {
    public WebDriver driver;
    public LoginPage login;
    public HomePage homePage;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header(){

        return new Header(driver);
    }
}

