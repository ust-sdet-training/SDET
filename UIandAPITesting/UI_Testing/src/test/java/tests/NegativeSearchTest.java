package tests;

import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;
import pages.SearchResultsPage;

public class NegativeSearchTest extends BaseTest {

    @Test
    public void verifyInvalidSearch() {
        HomePage home = new HomePage(driver);
        SearchResultsPage results = new SearchResultsPage(driver);
        home.searchProduct("nameofthegameisnone");
        assertFalse(results.isSearchResultsDisplayed());
    }
}