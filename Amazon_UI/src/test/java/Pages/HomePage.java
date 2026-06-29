package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {

        this.driver = driver;

    }

    By searchBox = By.id("twotabsearchtextbox");

    public void searchProduct(String product) {

        driver.findElement(searchBox).sendKeys(product + Keys.ENTER);

    }

}