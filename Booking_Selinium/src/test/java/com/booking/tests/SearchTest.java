package com.booking.tests;

import com.booking.pages.HomePage;
import com.booking.pages.ResultsPage;
import com.booking.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;

public class SearchTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    public void bookingSearchFlow() {
        HomePage home = new HomePage(driver);
        home.open();
        home.enterDestination("Chennai");
        home.selectSimpleDates("1", "5");
        home.setguests(2, 0, 1);
        ResultsPage results = home.search();
        assertTrue(results.isDisplayed(), "Results page should be displayed and contain property cards");
        assertTrue(results.getDestination().toLowerCase().contains("Chennai"), "Destination should contain Chennai");
        assertFalse(results.getDateRange().isEmpty(), "Selected date range should be retained on results page");
        assertTrue(results.getPropertyCount() > 0, "At least one property card should be displayed");
        var properties = results.getAllProperties();
        assertFalse(properties.isEmpty());
        var first = properties.get(0);
        assertTrue(first.hasDetails(), "Property card should have name or price info");
    }
}
