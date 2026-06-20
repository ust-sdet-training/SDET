package com.ust.sdet.pages;

import com.ust.sdet.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;


    protected BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public Header header(){
        return new Header(driver);
    }


    protected WebElement visible (By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));

    }
    protected List<WebElement> visibleElements(By by){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected List<WebElement> elements(By by){
        return driver.findElements(by);
    }
    protected  void click(By by){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();

    }

    protected void type(By by, CharSequence...  text){
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(text);
    }

    protected String text(By by){
        return visible(by).getText();
    }

    protected boolean isDisplayed(By by) {
        return !elements(by).isEmpty() && elements(by).get(0).isDisplayed();
    }
}

