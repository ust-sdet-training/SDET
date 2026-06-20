package example.org.pages.components;

import example.org.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class ProductCard {
    private static final By TITLE = By.cssSelector("[data-test='product-title']");
    private static final By PRICE = By.cssSelector("[data-test='product-price']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");

    private final WebElement root;
    private final WebDriver driver;
    public ProductCard(WebElement root, WebDriver driver){
        this.root = root;
        this.driver = driver;
    }

    public String title(){
        return root.findElement(TITLE).getText();
    }
    public int price(){
        return Integer.parseInt(root.findElement(PRICE).getText().replaceAll("[^0-9]",""));
    }
    public ProductPage open(){
        root.findElement(PRODUCT_LINK).click();
        return new ProductPage(driver);
    }

}
