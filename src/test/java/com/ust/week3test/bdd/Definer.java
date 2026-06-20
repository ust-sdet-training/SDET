package com.ust.week3test.bdd;

import com.ust.week3test.pages.CartPage;
import com.ust.week3test.pages.CatalogPage;
import com.ust.week3test.pages.CheckoutPage;
import com.ust.week3test.pages.ProductPage;
import com.ust.week3test.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class Definer {
    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header() {
        return new Header(driver);
    }
}
