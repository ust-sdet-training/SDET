package com.booking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ResultsPage extends BasePage {

    private static final By PROPERTY_CARDS = By.xpath("//div[@data-testid='property-card']");
    private static final By DESTINATION_TEXT = By.xpath("//h1 | //span[contains(@class, 'location')]");
    private static final By DATE_INFO = By.xpath("//span[contains(text(), '–')]");

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
            waitForElements(PROPERTY_CARDS);
            return getPropertyCount() > 0;

    }

    public int getPropertyCount() {
        return getElements(PROPERTY_CARDS).size();
    }

    public String getDestination() {
            return getText(DESTINATION_TEXT);
    }
    public String getDateRange() {
            return getText(DATE_INFO);
    }

    public List<Property> getAllProperties() {
        List<WebElement> cards = getElements(PROPERTY_CARDS);
        return cards.stream().map(Property::new).toList();
    }

    public static class Property {
        private final WebElement card;

        public Property(WebElement card) {
            this.card = card;
        }

        public String name() {
            return getCardText(".//h3 | .//a");
        }

        public String rating() {
            return getCardText(".//div[@data-testid='rating']");
        }

        public String price() {
            return getCardText(".//span[@data-testid='price'] | .//div[contains(@class, 'price')]");
        }

        public boolean hasDetails() {
            return !name().isEmpty() || !price().isEmpty();
        }

        private String getCardText(String xpath) {

                return card.findElement(By.xpath(xpath)).getText();

        }
    }
}
