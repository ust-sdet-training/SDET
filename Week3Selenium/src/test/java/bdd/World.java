package bdd;

import org.openqa.selenium.WebDriver;
import pages.CatalogPage;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.components.CartPage;
import pages.components.Header;
import pages.LoginPage;

public class World {

    public WebDriver driver;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;
    public LoginPage loginPage;

    public Header header() {
        return new Header(driver);
    }
}