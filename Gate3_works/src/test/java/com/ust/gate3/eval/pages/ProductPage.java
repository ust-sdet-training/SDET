package com.ust.gate3.eval.pages;

import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static java.util.stream.StreamSupport.stream;

public class ProductPage extends BasePage{

    private static final By DETAIL_NAME = By.cssSelector("[data-test='detail-name']");
    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");
    private static final By PRODUCT_SIZE = By.cssSelector("#shoe-size");
    private static final By PRODUCT_COLOR = By.cssSelector("input[name='color']");
    private static final By PRODUCT_QUANTITY = By.cssSelector("#quantity");
    private static final By PRODUCT_FULFILLMENT = By.cssSelector("input[name='fulfilment']");



    public ProductPage(WebDriver driver){
        super(driver);
        visible(DETAIL_NAME);
    }

    public String name(){
        return text(DETAIL_NAME);
    }

    public void updateProductDetails(String size, String color, int quantity, String fulfilment){
        if(size!=null){
            scrollIntoView(driver.findElement(PRODUCT_SIZE));
            new Select(driver.findElement(PRODUCT_SIZE)).selectByVisibleText(size);
        }

        if(color!=null){
            scrollIntoView(driver.findElement(PRODUCT_COLOR));
            driver.findElements(PRODUCT_COLOR).stream()
                    .filter(e -> e.getAttribute("value").equalsIgnoreCase(color))
                    .findFirst().ifPresent(WebElement::click);
        }

        if(quantity>0){
            WebElement qntty= driver.findElement(PRODUCT_QUANTITY);
            scrollIntoView(qntty);
            qntty.clear();
            qntty.sendKeys(String.valueOf(quantity));
        }

        if(fulfilment != null){
            scrollIntoView(driver.findElement(PRODUCT_FULFILLMENT));
            driver.findElements(PRODUCT_COLOR).stream()
                    .filter(e -> e.getAttribute("value").equalsIgnoreCase(fulfilment))
                    .findFirst().ifPresent(WebElement::click);
        }
    }

    public CartPage addToCart(){
        click(ADD_TO_CART);
        wait.until(ExpectedConditions.urlContains("/cart"));
        return new CartPage(driver);
    }

}
