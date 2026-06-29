package UI_Test;

import Purplle_Test.Factory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    @BeforeEach
    void setup() {
        webDriver = DriverFactory.createDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get("https://www.purplle.com/");
    }

    @AfterEach
    void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    public void input(By by, String input) {
        WebElement ele = visible(by);
        ele.clear();
        ele.sendKeys(input);
        ele.sendKeys(Keys.ENTER);
    }

    public WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void click(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public String text(By by) {
        return visible(by).getText();
    }

    public String text(WebElement element) {
        return element.getText();
    }
}