package com.ust.sdet.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage{
    public static WebDriver driver;
    public CartPage(WebDriver driver){
        super(driver);
    }

    public final By cartitems = By.cssSelector("#sc-active-cart");

    public final By delete = By.cssSelector("#sc-active-4126bb2c-ac86-46ec-8ec1-d0e55f7e058b > div.sc-list-item-content > div > div:nth-child(2) > div.a-row.sc-action-links > span.sc-action-quantity > span.sc-quantity-stepper > fieldset > div.a-stepper-inner-container > div > button:nth-child(1)");

    public void verifyTwoProducts(){
        List items = driver.findElements(cartitems);

        Assertions.assertEquals(2, items.size());
    }

    public void removeOneProduct() {

        driver.findElement(delete).click();

    }

    public void verifyOneProduct() {

        List items = driver.findElements(cartitems);

        Assertions.assertEquals(1, items.size());

    }

    public void refreshPage() {

        driver.navigate().refresh();

    }
}
