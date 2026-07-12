package com.shopkart.support;

import com.shopkart.api.ProductClient;
import com.shopkart.stepdefs.ApiEndToEndTest;
import com.shopkart.stepdefs.ApiOrderAccess;
import com.shopkart.stepdefs.ApiProductSearchTest;
import com.shopkart.ui.pages.*;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

public class World {
    public LoginPage login;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public Scenario scenario;
    public CheckoutPage checkout;
    public ProductClient client;
    public ApiEndToEndTest apiflow;
    public ApiProductSearchTest apiproductflow;
    public Response response;
}
