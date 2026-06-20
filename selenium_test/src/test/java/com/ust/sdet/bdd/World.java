package com.ust.sdet.bdd;

import org.openqa.selenium.WebDriver;

import com.ust.sdet.pages.*;

public class World {

    public WebDriver driver;

    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;
    public OrderConfirmPage orderConfirmed;

    public void initPages() {

        catalog = new CatalogPage(driver);

        product = new ProductPage(driver);

        cart = new CartPage(driver);

        checkout = new CheckoutPage(driver);

        orderConfirmed = new OrderConfirmPage(driver);
    }
}