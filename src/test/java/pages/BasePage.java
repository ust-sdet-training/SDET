package pages;

import pages.components.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Config;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public Header header()
    {
        return new Header(driver);
    }

    protected WebElement visible(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> visibleAll(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected List<WebElement> findby(By by)
    {
        return driver.findElements(by);
    }

    protected WebElement find(By by)
    {
        return driver.findElement(by);
    }

    protected void click(By by)
    {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected String togetText(By by)
    {
        return driver.findElement(by).getText();
    }

    protected int cardPrice(By by)
    {
        return Integer.parseInt(driver.findElement(by).getText().replaceAll("[^0-9]", ""));
    }

    protected String getTextvisible(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }

    protected int countBy(By by)
    {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
    }

    protected void countExpected(By by, String expectedCount)
    {
        wait.until(ExpectedConditions.textToBe(by, expectedCount));
    }

    protected void location()
    {
       driver.get(Config.catalogUrl());
    }
}
