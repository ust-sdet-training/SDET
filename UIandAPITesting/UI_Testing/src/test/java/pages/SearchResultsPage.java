package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {

    WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By products = By.className("product-base");
    private By firstProduct = By.xpath("(//li[contains(@class,'product-base')]//a)[1]");

    public boolean isSearchResultsDisplayed() {
        return driver.findElements(products).size() > 0;
    }

    public void clickFirstProduct() {
        driver.findElement(firstProduct).click();
    }

    public int getProductCount() {
        return driver.findElements(products).size();
    }
}