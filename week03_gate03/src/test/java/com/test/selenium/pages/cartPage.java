package com.test.selenium.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class cartPage extends basePage {
    private static final By CART_PAGE = By.cssSelector("[data-test='cart-page']");
    private static final By LINES = By.cssSelector("[data-test='cart-line']");
    private static final By TOTAL = By.cssSelector("[data-test='cart-total']");
    private static final By CHECKOUT = By.cssSelector("[data-test='checkout-button']");
    private static final By PRODUCT_ICON = By.cssSelector(".main-nav a[href='/catalog']");


    public cartPage(WebDriver driver) {
        super(driver);
        visible(CART_PAGE);
    }

    public int lineCount(){
        return elements(LINES).size();
    }

    public String total(){
        return text(TOTAL);
    }

    public checkOutPage proceed(){
        click(CHECKOUT);
        return new checkOutPage(driver);
    }

    public catalogPage switchBackToCatalog(){
        click(PRODUCT_ICON);
        return new catalogPage(driver);
    }


}
