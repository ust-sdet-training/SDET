package selenide.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import selenide.config.Config;
import selenide.pages.SelenideProductPage;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideProductTest {

    @BeforeEach
    void setUp(){
        Config.apply();
    }

    @Test
    @DisplayName("Checking for the Availabality")
    void verifyProductAvailability() {

        SelenideProductPage catalog = page(SelenideProductPage.class);

        catalog.openCatalog();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.search("Running");

        catalog.results()
                .shouldHave(size(1));

        catalog.clickProduct("Running Shoes");

        catalog.productTitle()
                .shouldHave(text("Running Shoes"));

        assertEquals("In stock",
                catalog.getMetaValue("AVAILABILITY"));
    }

    @Test
    @DisplayName("Check The Price")
    void checkPrice(){
        SelenideProductPage catalog = page(SelenideProductPage.class);

        catalog.openCatalog();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.search("Running");

        catalog.results()
                .shouldHave(size(1));

        catalog.clickProduct("Running Shoes");

        catalog.productTitle()
                .shouldHave(text("Running Shoes"));

        catalog.price()
                .shouldHave(text("Rs. 4,499"));
    }

    @Test
    @DisplayName("Check The Brand")
    void checkBrand(){
        SelenideProductPage catalog = page(SelenideProductPage.class);

        catalog.openCatalog();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.search("Running");

        catalog.results()
                .shouldHave(size(1));

        catalog.clickProduct("Running Shoes");

        catalog.productTitle()
                .shouldHave(text("Running Shoes"));


        assertEquals("SwiftRun",
                catalog.getMetaValue("BRAND"));
    }

    @Test
    @DisplayName("Check The Brand")
    void checkSku(){
        SelenideProductPage catalog = page(SelenideProductPage.class);

        catalog.openCatalog();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.search("Running");

        catalog.results()
                .shouldHave(size(1));

        catalog.clickProduct("Running Shoes");

        catalog.productTitle()
                .shouldHave(text("Running Shoes"));


        assertEquals("FT-SHOE-101",
                catalog.getMetaValue("SKU"));
    }

    @Test
    void verifyRunningProductExists() {

        SelenideProductPage page = new SelenideProductPage();

        page.openCatalog();

        page.results()
                .filterBy(text("Running Shoes"))
                .shouldHave(size(1));
    }

    @Test
    void verifyInvalidSearch() {

        SelenideProductPage page = new SelenideProductPage();

        page.openCatalog();

        page.search("Laptop123");

        page.results()
                .shouldHave(size(0));
    }
    @Test
    @DisplayName("Check The Add to cart button")
    void addTocart(){
        SelenideProductPage catalog = page(SelenideProductPage.class);

        catalog.openCatalog();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        catalog.search("Running");

        catalog.results()
                .shouldHave(size(1));

        catalog.clickProduct("Running Shoes");

        catalog.productTitle()
                .shouldHave(text("Running Shoes"));


        assertEquals("FT-SHOE-101",
                catalog.getMetaValue("SKU"));
        catalog.addToCart();

    }


}
