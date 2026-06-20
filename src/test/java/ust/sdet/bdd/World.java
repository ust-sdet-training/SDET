package ust.sdet.bdd;


import org.openqa.selenium.WebDriver;
import ust.sdet.pages.CartPage;
import ust.sdet.pages.CatalogPage;
import ust.sdet.pages.CheckoutPage;
import ust.sdet.pages.ProductPage;
import ust.sdet.pages.components.Header;

public class World {

    public WebDriver driver;

    public Header header(){
        return new Header(driver);
    }

    public CatalogPage catalog;
    public ProductPage products;
    public CartPage cart;
    public CheckoutPage checkout;

}
