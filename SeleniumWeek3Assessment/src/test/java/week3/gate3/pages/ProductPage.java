package week3.gate3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage{
    public ProductPage(WebDriver driver){
        super(driver);
    }

    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");

    public CartPage addToCart(){
        click(ADD_TO_CART);
        return new CartPage(driver);
    }

}
