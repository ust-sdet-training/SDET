package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By brandSearchIcon = By.cssSelector(".filter-search-iconSearch");
    private final By brandSearchBox = By.cssSelector(".filter-search-inputBox");
    private final By appliedFilter = By.cssSelector(".filter-summary-filter");
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void openBrandSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(brandSearchIcon))
                .click();
    }
    public void searchBrand(String brand) {
        wait.until(ExpectedConditions.elementToBeClickable(brandSearchIcon)).click();
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(brandSearchBox));
        searchBox.clear();
        searchBox.sendKeys(brand);
    }
    public void selectBrand(String brand) {
        By brandLocator = By.xpath("//label[contains(@class,'common-customCheckbox') and contains(.,'" + brand + "')]");
        WebElement brandElement = wait.until(ExpectedConditions.elementToBeClickable(brandLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", brandElement);
        brandElement.click();
    }
    public void waitForBrandFilterToApply(String brand) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                appliedFilter,
                brand));
    }

}