package com.ust.sdet.page;

import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static com.ust.sdet.support.Config.baseUrl;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js     = (JavascriptExecutor) driver;
    }

    public HomePage open() {
        driver.get(baseUrl());
        js.executeScript("Object.defineProperty(navigator,'webdriver',{get:()=>undefined})");
        wait.until(ExpectedConditions.titleContains("Purplle"));
        return this;
    }

    public ProductPage clickHomeSearchBox(String keyword) {
        By search = By.cssSelector("input[placeholder='What are you looking for?']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(search));
        js.executeScript("arguments[0].click();", driver.findElement(search));
        driver.findElement(search).sendKeys(keyword);

//        By realSearch = By.xpath(
//                "//*[@id="body"]/app-root/div/div/app-header/div[2]/desktop-search-content/div[1]/div/div[1]/p/input"
//        );
//        wait.until(ExpectedConditions.visibilityOfElementLocated(realSearch));
//        WebElement realSearchBox = driver.findElement(realSearch);
//        js.executeScript("arguments[0].value='" + keyword + "';", realSearchBox);
//        js.executeScript(
//                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
//                realSearchBox
//        );
//        By suggestion = By.xpath("//*[@id='searchsugg" + keyword + "']/span/span");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
//        wait.until(ExpectedConditions.elementToBeClickable(suggestion));
//        driver.findElement(suggestion).click();


        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("input"), 1));
        String encodedKeyword = keyword.replace(" ", "+");
        driver.get(Config.baseUrl()+"/search?q=" + encodedKeyword);
        wait.until(ExpectedConditions.urlContains("search"));
        return new ProductPage(driver);
    }
}