package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    private By brandName = By.className("pdp-title");
    private By productName = By.className("pdp-name");
    private By price = By.cssSelector("span.pdp-price strong");

    public boolean isBrandDisplayed() {
        return driver.findElement(brandName).isDisplayed();
    }

    public boolean isProductNameDisplayed() {
        return driver.findElement(productName).isDisplayed();
    }

    public boolean isPriceDisplayed() {
        return driver.findElement(price).isDisplayed();
    }

    public boolean isImageDisplayed() {
        return driver.getPageSource().contains("<img");
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public int getPrice() {
        String amount = driver.findElement(price).getText();
        amount = amount.replace("₹", "").replace(",", "").trim();
        return Integer.parseInt(amount);
    }
}