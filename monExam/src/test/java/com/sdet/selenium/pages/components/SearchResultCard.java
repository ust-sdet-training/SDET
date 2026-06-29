package com.sdet.selenium.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultCard {
    private final WebDriver driver;
    private final WebElement root;
    private final By titleLocator = By.cssSelector("h2 span, span.a-size-medium.a-color-base.a-text-normal");

    public SearchResultCard(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root = root;
    }

    public String getTitle() {
        try {
            WebElement titleElement = root.findElement(titleLocator);
            String text = titleElement.getText().trim();
            if (!text.isBlank()) {
                return text;
            }
        } catch (Exception ignored) {
        }

        String[] lines = root.getText().split("\\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            if (!firstLine.isBlank()) {
                return firstLine;
            }
        }
        return null;
    }

    public void open() {
        WebElement link = null;
        for (WebElement candidate : root.findElements(By.cssSelector("a[href]"))) {
            String href = candidate.getAttribute("href");
            if (href != null && (href.contains("/dp/") || href.contains("/gp/product") || href.contains("/product/"))) {
                link = candidate;
                break;
            }
        }
        if (link == null) {
            link = root;
        }

        try {
            link.click();
        } catch (Exception ignored) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
    }
}
