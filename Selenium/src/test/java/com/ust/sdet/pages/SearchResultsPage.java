package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchResultsPage extends BasePage{

    private static final By RESULT_TITLE = By.cssSelector("[aria-live=\"assertive\"]");
    private static final By HOTELS = By.cssSelector("[data-testid=\"filters-group-label-content\"]");
    private static final By PROPERTY_TYPES =
            By.cssSelector("[data-testid='property-card'] [data-testid='property-type']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage verifyPage() {
        visible(RESULT_TITLE);
        return this;
    }

    public SearchResultsPage filterByProperty(String propertyType) {
        By propertyLocator = By.xpath(
                "//label[contains(normalize-space(),'" + propertyType + "')]"
        );
        click(propertyLocator);
//        waitForPageToLoa();
        return this;
    }

    public SearchResultsPage verifyPropertyType(String expectedType) {

        List<WebElement> types = driver.findElements(PROPERTY_TYPES);

//        assertFalse(types.isEmpty(), "No properties found.");

        for (WebElement type : types) {
            assertTrue(
                    type.getText().equalsIgnoreCase(expectedType),
                    "Expected: " + expectedType +
                            " but found: " + type.getText()
            );
        }

        return this;
    }
}
