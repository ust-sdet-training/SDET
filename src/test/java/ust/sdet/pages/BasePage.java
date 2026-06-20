package ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ust.sdet.pages.components.Header;
import ust.sdet.support.Config;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCatalogPage(){
        driver.get(Config.Catalog());
    }

    public void openExistingCatalogPage(){
        driver.get(Config.Catalog());
    }

    public WebElement visible(By element){

        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));

    }
    public List<WebElement> visibleAll(By element){

        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));

    }
    public void type(By by, CharSequence... text){
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    public String text(By by){
        WebElement element = visible(by);
        return element.getText();
    }
    public void spinner(By by,String count){
        WebElement element = visible(by);
        wait.until((ignored)->{
            String resultCount = text(by);
            return !resultCount.equals("Searching products...")&&
            !resultCount.equals(count);
        });
    }
    public void click(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public List<WebElement> elements(By element){
        return driver.findElements(element);
    }
    public Header header(){
        return new Header(driver);
    }

}
