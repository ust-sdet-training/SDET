package bdd;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CatalogPage;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.components.Header;

public class World {

    WebDriver driver;
    CatalogPage catalog;
    ProductPage product;
    CartPage cart;
    CheckoutPage checkout;

    public Header header(){
        return new Header(driver);
    }

}
