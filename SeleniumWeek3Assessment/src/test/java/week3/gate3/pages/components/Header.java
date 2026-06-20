package week3.gate3.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import week3.gate3.pages.BasePage;
import week3.gate3.pages.CartPage;

public class Header extends BasePage {
    public Header(WebDriver driver){
        super(driver);
    }
    private static final By CART_LINK = By.cssSelector("[data-test='cart-icon']");

    public CartBadge cartBadge(){
        return new CartBadge(wait);
    }

    public CartPage openCart(){
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
        return new CartPage(driver);
    }
}
