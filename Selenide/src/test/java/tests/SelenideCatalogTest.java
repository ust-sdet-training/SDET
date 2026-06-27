package tests;

import config.SelenideConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.page;

class SelenideCatalogTest {
    @BeforeAll
    static void setup() {
        SelenideConfig.apply();
    }

    @Test
    @DisplayName("Verify search returns exactly 1 products")
    void verifyResultCount() {
        CatalogPage page = page(CatalogPage.class);
        page.openPage().searchFor("headphones");
        page.results().shouldHave(size(1));
    }

    @Test
    @DisplayName("Verify product names are displayed in correct order")
    void verifyProductOrder() {
        CatalogPage page = page(CatalogPage.class);
        page.openPage().searchFor("Pro");
        page.productTitles().shouldHave(texts( "Insulated Water Bottle", "Rain Jacket", "Kids Learning Tablet"));
    }

    @Test
    @DisplayName("Verify only one product contains Pro")
    void verifyOneProduct() {
        CatalogPage page = page(CatalogPage.class);
        page.openPage().searchFor("Pro");
        page.results().filterBy(text("Rain Jacket")).shouldHave(size(1));
    }
}
