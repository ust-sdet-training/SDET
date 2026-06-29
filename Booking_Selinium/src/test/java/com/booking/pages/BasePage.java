package com.booking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected static final int WAIT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
    }

    protected WebElement waitForElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected List<WebElement> waitForElements(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected void click(By by) {
        waitForClickable(by).click();
    }

    protected void type(By by, String text) {
        WebElement element = waitForElement(by);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By by) {
        return waitForElement(by).getText();
    }

    protected List<WebElement> getElements(By by) {
        return driver.findElements(by);
    }

    protected boolean isElementPresent(By by) {

        driver.findElement(by);
        return !getElements(by).isEmpty();


    }
}