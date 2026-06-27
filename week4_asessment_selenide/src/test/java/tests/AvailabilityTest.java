package tests;

import Base.BaseTest;
import pages.CatalogPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;

public class AvailabilityTest extends BaseTest {

    @Test
    void verifyProductAvailability() {

        page(CatalogPage.class)
                .searchFor("Yoga Mat")
                .viewProduct("Yoga Mat")
                .verifyProductIsInStock();
    }
}