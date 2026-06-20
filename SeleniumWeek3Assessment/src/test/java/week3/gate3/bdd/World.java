package week3.gate3.bdd;

import org.openqa.selenium.WebDriver;
import week3.gate3.pages.CartPage;
import week3.gate3.pages.CatalogPage;
import week3.gate3.pages.CheckoutPage;
import week3.gate3.pages.ProductPage;
import week3.gate3.pages.components.Header;
import week3.gate3.pages.components.ProductCard;

public class World {
    public WebDriver driver;
    public CatalogPage catalog;
    public CartPage cart;
    public CheckoutPage checkout;
    public ProductPage product;
    public ProductCard card;

    public Header header(){
        return new Header(driver);
    }
}
