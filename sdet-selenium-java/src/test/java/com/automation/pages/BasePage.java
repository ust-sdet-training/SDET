package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> elements(By by) {
        return driver.findElements(by);
    }

    protected void click(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void type(By by, CharSequence... text) {
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(text);
    }

    protected void pressEnter(By by){
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(Keys.ENTER);
    }

    protected String text(By by) {
        return visible(by).getText();
    }

    protected void selectOption(By by,String value){
        WebElement element = driver.findElement(by);
        Select select = new Select(element);
        select.selectByValue(value);
    }
}
