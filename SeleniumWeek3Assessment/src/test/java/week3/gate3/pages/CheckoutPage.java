package week3.gate3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage{
    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    private static final By ORDER_BTN = By.cssSelector("[data-test='place-order']");
    private static final By CONFIRMATION = By.cssSelector("[data-test='order-confirmation']");

    public CheckoutPage placeOrder(){
        click(ORDER_BTN);
        return this;
    }

    public String confirmOrder(){
        return text(CONFIRMATION);
    }


}
