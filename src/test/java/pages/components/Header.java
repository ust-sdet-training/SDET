package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.CartPage;

public class Header extends BasePage
{

    private static final By CART_ICON=By.cssSelector("[data-test='cart-icon']");

    public Header(WebDriver driver)
    {
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(driver);

    }

    public CartPage openCart(){
        click(CART_ICON);
        return new CartPage(driver);
    }
}
