package com.example.Selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By productName =
            By.cssSelector("h1, [class*='product'][class*='name'], [class*='Product'][class*='Name']");

    private final By productPrice =
            By.cssSelector("strong.our-price, [class*='our-price']");

    private final By productRating =
            By.cssSelector("p.star-rating");

    private final By addToCartButton =
            By.xpath("//button[contains(.,'Add to Cart')]");

    private final By viewCartButton =
            By.cssSelector(".modal.show button.btn-primary");

    public String getProductName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productName));

        return firstVisibleText(productName);
    }

    public String getProductPrice() {
        return wait.until(driver -> {
            String price = firstVisibleText(productPrice);

            if (!price.isEmpty()) {
                return price;
            }

            return null;
        });
    }

    public String getProductRating() {
        String rating = firstVisibleText(productRating);

        if (rating.isEmpty()) {
            return "Rating Not Available";
        }

        return rating;
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
    }

    private String firstVisibleText(By locator) {
        List<WebElement> elements = driver.findElements(locator);

        for (WebElement element : elements) {
            String text = element.getText().trim();

            if (element.isDisplayed() && !text.isEmpty()) {
                return text;
            }
        }

        return "";
    }
}
