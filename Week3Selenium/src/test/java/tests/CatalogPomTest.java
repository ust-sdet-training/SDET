package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;
import pages.ProductPage;
import pages.components.CartPage;
import support.DriverFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogPomTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Exercise 1 : POM search query returns only matching catalog titles")
    void searchFindsOnMatchingProduct() {

        CatalogPage catalog = new CatalogPage(driver)
                .open()
                .searchFor("headphones");

        List<String> titles = catalog.titles();

        assertAll(
                () -> assertFalse(titles.isEmpty(), "search returned no products"),
                () -> assertTrue(
                        titles.stream()
                                .allMatch(title ->
                                        title.toLowerCase().contains("headphones")),
                        "search result should be related to headphones"
                )
        );
    }

    @Test
    @DisplayName("Exercise 2 : POM sort hides the stale-element handling inside the page")
    void sortLowToHighOnPom() {

        List<Integer> prices = new CatalogPage(driver)
                .open()
                .sortBy("Price: Low to High")
                .prices();

        assertEquals(
                prices.stream().sorted().toList(),
                prices
        );
    }

    @Test
    @DisplayName("POM header component expresses cart badge and cart navigation")
    void headerComponentOpenCart() {

        CatalogPage catalog = new CatalogPage(driver)
                .open();

        catalog.header()
                .cartBadge()
                .expectCount(0);

        CartPage cart = catalog.header()
                .openCart();

        assertEquals(0, cart.lineCount());
    }

    @Test
    @DisplayName("Exercise 3 : Catalog flow confirms the order from catalog to checkout")
    void catalogToConfirmOrder() {

        CatalogPage catalog = new CatalogPage(driver)
                .open()
                .searchFor("headphones");

        ProductPage product = catalog.openFirstProduct();

        assertTrue(
                product.name()
                        .toLowerCase()
                        .contains("headphones")
        );

        CartPage cart = product.addToCart();

        cart.header()
                .cartBadge()
                .expectCount(1);

        assertAll(
                () -> assertEquals(1, cart.lineCount()),
                () -> assertFalse(cart.total().isBlank())
        );

        String confirmation = cart.proceed()
                .placeOrder()
                .confirmationText();

        assertTrue(
                confirmation.toLowerCase()
                        .contains("confirmed")
        );
    }
}