package com.ust.sdet.tests;

import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static com.ust.sdet.support.Config.baseUrl;

public class Product_Search_Dynamic_ValidationTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    @DisplayName("As a user, I want to search for beauty products and validate product details so that I can choose relevant items.")
    void purple() throws InterruptedException
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

       //1.Navigate → https://www.purplle.com
        driver.get(baseUrl());


        //1. Search → “face wash”
        var search = By.cssSelector("input[placeholder='What are you looking for?'");
        driver.findElement(search).isDisplayed();
        driver.findElement(search).click();

        driver.findElement(search).sendKeys("face wash");
        driver.findElement(search).click();
        driver.findElement(search).sendKeys(Keys.ENTER);
        driver.get(baseUrl()+"/search?q=face%20wah&is_form_search=1");


        //2. Apply filter:
        //    * Price range
        //    * Category/Brand
        //3. Apply sorting:
        wait.until((ExpectedConditions.urlContains("face")));
        driver.navigate().to(driver.getCurrentUrl());
       wait.until(ExpectedConditions.urlToBe(baseUrl()+"/search?q=face%20wah&is_form_search=1"));

        var brand=By.cssSelector("#filterHeader > div > div > ul > li:nth-child(2) > a > span.tx-blk1i");
        wait.until(ExpectedConditions.visibilityOfElementLocated(brand));
        driver.findElement(brand).isDisplayed();
        driver.findElement(brand).click();


        //  * Price low → high
        var price = By.cssSelector("#filterHeader > div > div > ul > li:nth-child(3) > a > span.tx-blk1i");
        wait.until(ExpectedConditions.visibilityOfElementLocated(price));
        driver.findElement(price).isDisplayed();
        driver.findElement(price).click();


        //        //4. Capture product list
        var list_firstProduct = By.cssSelector("#listing_item_353947 > div.m-0.mt-3.position-relative.ng-star-inserted > div.product-title.fs-7.text-start.text-black.fw-normal");
        wait.until(ExpectedConditions.visibilityOfElementLocated(list_firstProduct));
        driver.findElement(list_firstProduct).isDisplayed();

    }
}
