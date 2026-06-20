package com.test.selenium.bbdFeature;

import com.test.selenium.pages.ProductPage;
import com.test.selenium.pages.cartPage;
import com.test.selenium.pages.catalogPage;
import com.test.selenium.pages.checkOutPage;
import com.test.selenium.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class globalAcc {


    public WebDriver driver;
    public catalogPage catalog;
    public ProductPage product;
    public cartPage cart;
    public checkOutPage checkout;

    public Header header() {
        return new Header(driver);
    }

}