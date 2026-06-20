package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage
{
    private static final By PROCEED_CHECK = By.cssSelector("[data-test ='checkout-button']");
    private static final By LINES = By.cssSelector("[data-test ='cart-line']");
    private static final By TOTAL = By.cssSelector("[data-test ='cart-total']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int lineCount() {
        return findby(LINES).size();
    }

    public String total() {
        return getTextvisible(TOTAL);
    }

    public CheckoutPage cartButton()
    {
        click(PROCEED_CHECK);
        return new CheckoutPage(driver);
    }

}
