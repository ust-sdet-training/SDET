package ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{

    private static final By CHECKOUT =By.cssSelector("[data-test='checkout-button']");

    private static final By LINES = By.cssSelector("[data-test='cart-line']");
    public CartPage(WebDriver driver){
        super(driver);
    }

    public int lineCount(){
        return elements(LINES).size();
    }
    public CheckoutPage proceed(){
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }

}
