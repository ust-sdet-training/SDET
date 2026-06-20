
package example.org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_LINE =
            By.cssSelector("[data-test='cart-line']");

    private static final By CHECKOUT_BUTTON =
            By.cssSelector("[data-test='checkout-button']");

    public CartPage(WebDriver driver){
        super(driver);
    }

    public int lineCount(){
        return element(CART_LINE).size();
    }

    public CheckoutPage proceed(){
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

}