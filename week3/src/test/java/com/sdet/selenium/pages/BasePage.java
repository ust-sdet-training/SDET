package com.sdet.selenium.pages;
import com.sdet.selenium.pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.Element;
import java.time.Duration;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public Header header() {
        return new Header(driver);
    }

    protected WebElement visible(By el) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(el));
    }
    protected List<WebElement> visibleElements(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected List<WebElement> elements(By el) {
        return driver.findElements(el);
    }

    protected  void click(By el)
    {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();

    }
    protected  String text (By el)
    {
        return visible(el).getText();
    }

    protected void type(By by,CharSequence... text)
    {
        WebElement element =visible(by);
        element.clear();
        element.sendKeys(text);
    }
}
