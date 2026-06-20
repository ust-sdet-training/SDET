package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By PLACE_ORDER = By.cssSelector("[data-test='place-order']");
    private static final By CONFIRMATION = By.cssSelector("[data-test='order-confirmation'] p:nth-of-type(2)");

    protected CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage placeorder()
    {
         click(PLACE_ORDER);
         return this;
    }

    public CheckoutPage confirmationText()
    {
        getTextvisible(CONFIRMATION);
        return this;
    }

}
