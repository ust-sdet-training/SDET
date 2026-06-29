package ust.com.sdet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UITesting {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://www.amazon.com";
    private static final String SEARCH_TERM = "headphones";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void filterAndSortingValidation() {
        driver.get(BASE_URL);


        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        searchBox.clear();
        searchBox.sendKeys(SEARCH_TERM);
        searchBox.submit();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.s-main-slot")));

    }
}