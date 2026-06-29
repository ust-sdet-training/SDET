package com.ust.sdet.tests;

import com.ust.sdet.pages.HomePage;
import com.ust.sdet.pages.SearchResultsPage;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void teardown() {
        if(driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Filter By price")
    void verifyFilterByPrice() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        homePage.open();
        homePage.search("Chennai", "2026-07-15", "2026-07-18");
        searchResultsPage.verifyPage();
        searchResultsPage.filterByProperty();
    }
}
