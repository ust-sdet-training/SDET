package ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By INSUFFICIENT_STOCK = By.cssSelector(".alert");

    private static final By AddCartButton =
            By.cssSelector("[data-test='add-to-cart']");

    private static WebDriver driver;
    public ProductPage(WebDriver driver){
        super(driver);
    }
    public CartPage addToCart(){

        click(AddCartButton);

        return new CartPage(driver);
    }
    public String insufficientStockText(){
        return text(INSUFFICIENT_STOCK);
    }

}
