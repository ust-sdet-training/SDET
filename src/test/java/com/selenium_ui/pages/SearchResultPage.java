package com.selenium_ui.pages;

import com.selenium_ui.pages.components.FlightCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchResultPage extends BasePage {
    private static final By FLIGHT_CARDS = By.className("listing");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public List<FlightCard> flights() {

        return visibleAll(FLIGHT_CARDS)
                .stream()
                .map(FlightCard::new)
                .toList();
    }
}