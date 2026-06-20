package com.ust.gate3.eval.bdd;

import com.ust.gate3.eval.pages.CartPage;
import com.ust.gate3.eval.pages.CatalogPage;
import com.ust.gate3.eval.pages.CheckoutPage;
import com.ust.gate3.eval.pages.ProductPage;
import com.ust.gate3.eval.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class World{
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header(){
        return new Header(driver);
    }
}
