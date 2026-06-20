package org.selenium.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By CART_LINES = By.cssSelector("[data-test='cart-line']");
    private static final By PROCEED = By.cssSelector("button.button.primary");

    public CartPage(WebDriver driver){
        super(driver);
    }

    public int lineCount(){
        return elements(CART_LINES).size();
    }

    public CheckoutPage proceed(){
        click(PROCEED);
        return new CheckoutPage(driver);
    }
}
