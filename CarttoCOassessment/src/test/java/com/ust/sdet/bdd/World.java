package com.ust.sdet.bdd;

import com.ust.sdet.pages.*;
import com.ust.sdet.pages.component.Header;
import org.openqa.selenium.WebDriver;

public class World {

    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkoutPage;
    public ConfirmationPage confirmationPage;


    public Header header(){
        return new Header(driver);
    }

}