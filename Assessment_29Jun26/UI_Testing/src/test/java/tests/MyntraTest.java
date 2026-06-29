package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.SearchResultsPage;
import driver.DriverFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyntraTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){

        driver = DriverFactory.getDriver();

        driver.get("https://www.myntra.com/");
    }

    @AfterEach
    public void tearDown(){

        DriverFactory.quitDriver();
    }

    @Test
    public void verifyFiltersAndSorting(){

        HomePage home = new HomePage(driver);

        SearchResultsPage results =
                home.searchProduct("T-Shirts");

        results.selectBrand("Nike");

        List<String> brands =
                results.getDisplayedBrands();

        for(String brand : brands){

            assertEquals("Nike", brand);
        }

        results.applyPriceFilter();

        List<Integer> prices =
                results.getPrices();

        for(Integer price : prices){

            assertTrue(price >= 500);

            assertTrue(price <= 1000);
        }

        results.sortLowToHigh();

        assertTrue(results.pricesAreSorted());
    }
}