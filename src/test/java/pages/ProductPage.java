package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By ADD = By.cssSelector("[data-test='add-to-cart']");
    private static final By ADD_TITLE = By.cssSelector("[data-test='detail-name']");

    protected ProductPage(WebDriver driver)
    {
        super(driver);
    }


    public String addToCartTitle() {
        return togetText(ADD_TITLE);

    }

    public CartPage addToCart()
    {
        click(ADD);
        return new CartPage(driver);

    }

}
