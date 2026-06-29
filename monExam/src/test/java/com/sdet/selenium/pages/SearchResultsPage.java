package com.sdet.selenium.pages;

import com.sdet.selenium.pages.components.SearchResultCard;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchResultsPage extends BasePage {
    private final By resultCards = By.cssSelector("div[data-component-type='s-search-result'], div.s-result-item");
    private final By noResultsMessage = By.cssSelector("div.s-no-result, div[data-component-type='s-no-result'], .a-section .s-no-results");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return !findElements(resultCards).isEmpty() || !findElements(noResultsMessage).isEmpty();
    }

    public List<String> getDisplayedProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement result : findElements(resultCards)) {
            String title = new SearchResultCard(driver, result).getTitle();
            if (title != null && !title.isBlank()) {
                names.add(title);
            }
        }
        return names;
    }

    public boolean hasNoResults() {
        if (!findElements(noResultsMessage).isEmpty()) {
            return true;
        }

        boolean noProducts = findElements(resultCards).isEmpty();
        String bodyText = driver.findElement(By.tagName("body")).getText().toLowerCase();
        return noProducts || bodyText.contains("did not match any products")
                || bodyText.contains("no results for")
                || bodyText.contains("we did not find any results for")
                || bodyText.contains("sorry, we couldn't find any results")
                || bodyText.contains("try a different search")
                || bodyText.contains("no results found");
    }

    public void openFirstProduct() {
        List<WebElement> cards = findElements(resultCards);
        if (cards.isEmpty()) {
            throw new IllegalStateException("No products available in the search results");
        }

        String originalWindow = driver.getWindowHandle();
        new SearchResultCard(driver, cards.get(0)).open();

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.numberOfWindowsToBe(2),
                    ExpectedConditions.urlContains("/dp/"),
                    ExpectedConditions.urlContains("/gp/product"),
                    ExpectedConditions.urlContains("/product/"),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("#productTitle, h1, [id*='title']"))
            ));
        } catch (TimeoutException ignored) {
            // Amazon may keep the user on the same tab; the later assertions will validate the page state.
        }

        if (driver.getWindowHandles().size() > 1) {
            Set<String> handles = driver.getWindowHandles();
            handles.remove(originalWindow);
            if (!handles.isEmpty()) {
                driver.switchTo().window(handles.iterator().next());
            }
        }
    }
}
