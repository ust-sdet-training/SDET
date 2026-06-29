package pages;

import pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By searchBox =
            By.cssSelector("input.desktop-searchBar");

    public HomePage(WebDriver driver){

        super(driver);
    }

    public SearchResultsPage searchProduct(String product){

        type(searchBox, product);

        driver.findElement(searchBox)
                .sendKeys(Keys.ENTER);

        return new SearchResultsPage(driver);
    }
}