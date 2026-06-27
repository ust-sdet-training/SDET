package selenide.tests;

import selenide.pages.CatalogPage;
import selenide.pages.ProductPage;
import selenide.Configurration.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.page;

public class CatalogTest {
    @BeforeEach
    void setUp(){
        Config.apply();
    }


    @Test
    @DisplayName("Assert the availability badge of product")
    void assertAvailabilityOfProduct(){
        CatalogPage c = page(CatalogPage.class);
        ProductPage product = page(ProductPage.class);
        c.open();
        c.searchFor("running").results().shouldHave(sizeGreaterThan(0));
        c.openItem();
        product.nameIsVisible().getAvailability().shouldHave(text("In stock"));

    }
}
