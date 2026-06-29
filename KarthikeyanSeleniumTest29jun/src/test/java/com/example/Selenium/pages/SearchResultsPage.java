package com.example.Selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Product Name Locator
    private final By productLinks =
            By.cssSelector("a[id^='listing_item_']");

    // Print All Products
    public void printAllProducts() {

        List<WebElement> allProducts =
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productLinks, 0));

        System.out.println("Total Products Found : " + allProducts.size());

        for (int i = 0; i < allProducts.size(); i++) {

            String productName =
                    allProducts.get(i).getAttribute("data-evlbl");

            if (productName == null || productName.isEmpty()) {
                productName = allProducts.get(i).getAttribute("title");
            }

            if (productName == null) {
                productName = "";
            }

            if (!productName.isEmpty()) {

                System.out.println((i + 1) + " -> " + productName);
            }
        }
    }

    // Click First Product Dynamically
    public void clickFirstProduct() {

        WebElement firstProduct =
                wait.until(ExpectedConditions.elementToBeClickable(productLinks));

        String productUrl = firstProduct.getAttribute("href");

        if (productUrl == null || productUrl.isEmpty()) {
            firstProduct.click();
        } else {
            driver.get(productUrl);
        }

        wait.until(ExpectedConditions.urlContains("/product/"));
    }
}
