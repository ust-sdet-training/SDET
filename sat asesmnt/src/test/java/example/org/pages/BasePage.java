package example.org.pages;

import example.org.pages.components.Header;
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

    protected List<WebElement> element(By by){
        return driver.findElements(by);
    }

    protected void click(By by){
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
        try{
            el.click();
        }catch(org.openqa.selenium.ElementClickInterceptedException ex){
            
            ((org.openqa.selenium.JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", el);
            try{ Thread.sleep(200); }catch(InterruptedException ignored){}
            try{
                el.click();
            }catch(org.openqa.selenium.ElementClickInterceptedException ex2){
                ((org.openqa.selenium.JavascriptExecutor)driver).executeScript("arguments[0].click();", el);
            }
        }
    }

    protected void type(By by, CharSequence... text){
        WebElement element = visible(by);
        element.clear();
        element.sendKeys(text);
    }

    protected String text(By by){
        return visible(by).getText();
    }
}
