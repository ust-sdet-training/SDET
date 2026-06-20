package ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ust.sdet.pages.BasePage;
import ust.sdet.pages.CartPage;

public class Header extends BasePage {
    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon'");

    public Header(WebDriver driver){
        super(driver);
    }

    public CartBadge cartBadge(){
        return new CartBadge(wait);
    }

    public CartPage opencart(){
        click(CART_ICON);
        return new CartPage(driver);
    }
    public CartPage openProducts(){
        click(CART_ICON);
        return new CartPage(driver);
    }
}
