package com.sdet.selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.sdet.selenium.support.DriverFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmazonProductSearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        System.setProperty("headless", "true");
        driver = DriverFactory.createChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void SearchForWirelessMouse() {
        driver.get("https://www.amazon.in");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#twotabsearchtextbox, input[name='field-keywords']")));
        WebElement searchBox = driver.findElement(By.cssSelector("#twotabsearchtextbox, input[name='field-keywords']"));
        searchBox.clear();
        searchBox.sendKeys("wireless mouse");

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-search-submit-button, input[type='submit']")));
        searchButton.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("s?k=wireless+mouse"),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-component-type='s-search-result']"))
        ));

        assertTrue(driver.getCurrentUrl().contains("wireless") || driver.getPageSource().contains("wireless mouse"),
                "Search results page should load for the requested product");

        List<String> productNames = captureDisplayedProductNames();
        assertFalse(productNames.isEmpty(), "At least one product name should be displayed on the first results page");

        String selectedProductName = productNames.get(0);
        WebElement firstResult = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']")).get(0);
        WebElement resultLink = findResultLink(firstResult);
        String originalWindow = driver.getWindowHandle();
        resultLink.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.numberOfWindowsToBe(2),
                ExpectedConditions.urlContains("/dp/"),
                ExpectedConditions.urlContains("/gp/product"),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#productTitle, h1"))
        ));

        if (driver.getWindowHandles().size() > 1) {
            Set<String> handles = driver.getWindowHandles();
            handles.remove(originalWindow);
            if (!handles.isEmpty()) {
                driver.switchTo().window(handles.iterator().next());
            }
        }

        String productTitle = getProductTitle();
        assertNotNull(productTitle, "Product title should be displayed");
        assertFalse(productTitle.isBlank(), "Product title should not be empty");

        String productPrice = getProductPrice();
        assertNotNull(productPrice, "Product price should be available");
        assertFalse(productPrice.isBlank(), "Product price should not be empty");

        String productRating = getProductRating();
        if (productRating != null && !productRating.isBlank()) {
            assertTrue(productRating.toLowerCase().contains("star") || productRating.toLowerCase().contains("rating") || productRating.matches(".*\\d+.*"),
                    "Product rating should be shown when available");
        }

        String pageTitle = driver.getTitle();
        String productTitleFromPage = getProductTitle();
        assertTrue(pageTitle.toLowerCase().contains("amazon") || productTitleFromPage != null,
                "Product page should display a recognizable product page");
        if (productTitleFromPage != null && !productTitleFromPage.isBlank()) {
            assertTrue(productTitleFromPage.toLowerCase().contains(selectedProductName.toLowerCase())
                            || selectedProductName.toLowerCase().contains(productTitleFromPage.toLowerCase().split(" ")[0]),
                    "Product page should display the selected product name or a closely related title");
        }
    }

    private List<String> captureDisplayedProductNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> results = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));

        for (WebElement result : results) {
            String name = null;
            try {
                name = result.findElement(By.cssSelector("h2 span")).getText().trim();
            } catch (Exception ignored) {
                try {
                    name = result.findElement(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal")).getText().trim();
                } catch (Exception ignoredAgain) {
                    name = result.getText().split("\\n")[0].trim();
                }
            }
            if (!name.isBlank()) {
                names.add(name);
            }
        }
        return names;
    }

    private WebElement findResultLink(WebElement resultCard) {
        List<WebElement> links = resultCard.findElements(By.cssSelector("a[href]"));
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && (href.contains("/dp/") || href.contains("/gp/product") || href.contains("/product/"))) {
                return link;
            }
        }
        return resultCard;
    }

    private String getProductTitle() {
        List<WebElement> titleCandidates = driver.findElements(By.cssSelector("#productTitle, h1"));
        for (WebElement candidate : titleCandidates) {
            String text = candidate.getText().trim();
            if (!text.isBlank()) {
                return text;
            }
        }
        return null;
    }

    private String getProductPrice() {
        List<By> priceLocators = List.of(
                By.cssSelector("#corePriceDisplay_desktop_feature_div .a-price .a-offscreen"),
                By.cssSelector("#priceblock_ourprice"),
                By.cssSelector("span.a-price .a-offscreen"),
                By.xpath("//*[contains(@class,'price') or contains(@id,'price')][not(contains(@class,'price-tip'))]")
        );

        for (By locator : priceLocators) {
            List<WebElement> candidates = driver.findElements(locator);
            for (WebElement candidate : candidates) {
                String text = candidate.getText().trim();
                if (!text.isBlank()) {
                    return text;
                }
            }
        }
        return null;
    }

    private String getProductRating() {
        List<WebElement> ratingCandidates = driver.findElements(By.cssSelector("[aria-label*='stars'], [aria-label*='star'], .a-icon-star"));
        for (WebElement candidate : ratingCandidates) {
            String text = candidate.getText().trim();
            String ariaLabel = candidate.getAttribute("aria-label");
            if (!text.isBlank()) {
                return text;
            }
            if (ariaLabel != null && !ariaLabel.isBlank()) {
                return ariaLabel;
            }
        }
        return null;
    }
}
