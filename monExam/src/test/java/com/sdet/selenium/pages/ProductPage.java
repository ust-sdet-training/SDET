package com.sdet.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {
    private final By title = By.cssSelector("#productTitle, h1, [id*='title']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        List<WebElement> titleCandidates = findElements(title);
        for (WebElement candidate : titleCandidates) {
            String text = candidate.getText().trim();
            if (!text.isBlank()) {
                return text;
            }
        }

        String pageTitle = getPageTitle();
        if (pageTitle != null && !pageTitle.isBlank()) {
            return pageTitle;
        }
        return null;
    }

    public String getPriceText() {
        List<By> priceLocators = List.of(
                By.cssSelector("#corePriceDisplay_desktop_feature_div .a-price .a-offscreen"),
                By.cssSelector("#priceblock_ourprice"),
                By.cssSelector("span.a-price .a-offscreen"),
                By.cssSelector(".a-price .a-offscreen"),
                By.cssSelector("[id*='price']")
        );
        for (By locator : priceLocators) {
            for (WebElement candidate : findElements(locator)) {
                String text = candidate.getText().trim();
                if (!text.isBlank()) {
                    return text;
                }
            }
        }

        String pageText = driver.findElement(By.tagName("body")).getText();
        if (pageText.contains("₹") || pageText.contains("$") || pageText.contains("€")) {
            return pageText;
        }
        return null;
    }

    public String getRatingText() {
        List<WebElement> ratingCandidates = findElements(By.cssSelector("[aria-label*='stars'], [aria-label*='star'], .a-icon-star"));
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
