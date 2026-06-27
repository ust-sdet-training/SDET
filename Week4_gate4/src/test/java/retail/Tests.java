package retail;

import org.junit.jupiter.api.DisplayName;
import com.ust.sdet.selenide.pages.CatalogPage;
import com.ust.sdet.selenide.pages.ProductPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class Tests {


    @Test
    @DisplayName("Verify catalog search and product details")
    void testCatalogSearchAndProductDetails() {

        open("http://localhost:5173/catalog");

        CatalogPage catalog = page(CatalogPage.class);

        catalog.searchFor("running shoes");

        // Verify search results
        catalog.results()
                .shouldHave(size(1))
                .shouldHave(texts("Running Shoes"));

        catalog.results()
                .first()
                .shouldBe(visible);

        // Open product
        catalog.openFirstProduct();

        // Verify product page loaded
        webdriver().shouldHave(urlContaining("/product/running-shoes"));
        $("body").shouldBe(visible);
    }
}