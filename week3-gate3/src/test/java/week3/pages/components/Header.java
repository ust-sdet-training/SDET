package week3.pages.components;

import week3.pages.BasePage;
import week3.pages.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {

    private static final By CART_LINK = By.cssSelector("a[href='/cart']");

    public Header(WebDriver driver) {
        super(driver);
    }

    public CartBadge cartBadge() {
        return new CartBadge(wait);
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }
}