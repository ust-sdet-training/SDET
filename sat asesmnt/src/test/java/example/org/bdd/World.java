package example.org.bdd;

import example.org.pages.*;
import example.org.pages.components.Header;
import org.openqa.selenium.WebDriver;

public class World {
    public WebDriver driver;
    public HomePage home;
    public ProductPage product;
    public CatalogPage catalog;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header(){
        return new Header(driver);
    }

}
