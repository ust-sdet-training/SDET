package week3.gate3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import week3.gate3.pages.components.Header;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public Header header(){
        return new Header(driver);
    }

    protected WebElement visible(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> visibleElements(By by){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected void click(By by){
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected String text(By by){
        return visible(by).getText();
    }

    protected List<WebElement> elements(By by){
        return driver.findElements(by);
    }


}
