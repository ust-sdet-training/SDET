package week3.bdd;

import week3.pages.*;
import week3.pages.components.Header;
import org.openqa.selenium.WebDriver;
public class World {

    public WebDriver driver;
    public LoginPage login;
    public HomePage homePage;
    public CatalogPage catalog;
    public ProductPage product;
    public CartPage cart;
    public CheckoutPage checkout;

    public Header header() {
        return new Header(driver);
    }


}