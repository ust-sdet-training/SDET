package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By searchBox = By.cssSelector("input.desktop-searchBar");

    public void searchProduct(String product) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(product);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
    }
}