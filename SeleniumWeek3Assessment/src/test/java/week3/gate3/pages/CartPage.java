package week3.gate3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{
    public CartPage(WebDriver driver){
        super(driver);
    }

    private static final By CART_COUNT = By.cssSelector("[data-test='cart-count']");
    private static final By CART_ITEMS = By.cssSelector("[data-test='cart-line']");
    private static final By CHECKOUT_BTN = By.cssSelector("[data-test='checkout-button'");

    public int cartCount(){
        return Integer.parseInt(text(CART_COUNT));
    }

    public int cartElementCount(){
        return elements(CART_ITEMS).size();
    }

    public CheckoutPage checkout(){
        click(CHECKOUT_BTN);
        return new CheckoutPage(driver);
    }
}
