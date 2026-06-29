package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Config;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;

    protected  final WebDriverWait wait;

    protected BasePage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement visible(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> visibleAll(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected List<WebElement> findBy(By by)
    {
        return driver.findElements(by);
    }

    protected WebElement find(By by)
    {
        return driver.findElement(by);
    }

    protected List<WebElement> findAll(By by)
    {
        return driver.findElements(by);
    }

    protected String togetText(By by)
    {
        return driver.findElement(by).getText();
    }

    protected void click(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void SearchCount(By by, String count)
    {
        wait.until(ExpectedConditions.textToBe(by , count));
    }
    protected int cardPrice(By by)
    {
        return Integer.parseInt(driver.findElement(by).getText().replaceAll("[^0-9]", ""));
    }

    protected void location()
    {
        driver.get(Config.baseUrl());
    }

    protected String getTextvisible(By by)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }



}

