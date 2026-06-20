package com.ust.cucumber.pages;

import com.ust.cucumber.pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected  static WebDriver driver;
    protected  static WebDriverWait wait;

    protected  BasePage(WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));

        }
    protected  WebElement visible(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    protected List<WebElement> visibleAll(By by){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
    protected List<WebElement> elements(By by){
        return driver.findElements(by);
    }
    protected void type(By by,CharSequence... text){
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }
    protected void click(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }
    protected String text(By by){
        return driver.findElement(by).getText();
    }



    protected void staleness(WebElement web,By by){
        wait.until(ExpectedConditions.stalenessOf(web));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by,0));
    }

    public Header header() {
        return new Header(driver);
    }
}
