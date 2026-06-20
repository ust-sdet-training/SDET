package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.CheckoutPage;

public class CartPage extends BasePage {

    private static final By TITLE =
            By.id("cart-title");

    private static final By ROWS =
            By.cssSelector(".cart-row");

    private static final By CHECKOUT =
            By.linkText("Checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(TITLE);
    }

    public Header header() {
        return new Header(driver);
    }

    public int lineCount() {
        return driver.findElements(ROWS).size();
    }

    public String total() {
        return driver.getPageSource();
    }

    public CheckoutPage proceed() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }
}