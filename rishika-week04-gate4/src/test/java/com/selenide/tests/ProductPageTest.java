package com.selenide.tests;
import com.codeborne.selenide.SelenideConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.selenide.pages.ProductPage;
import com.selenide.Support.Config;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
public class ProductPageTest {
    @BeforeAll
    static void setup() {
        Config.init();
    }
    @Test
    void openFirstProduct() {
        open("/catalog");
        $("[data-test='product-card'] a")
                .shouldBe(visible)
                .click();
        ProductPage productPage = page(ProductPage.class);
        productPage
                .shouldBeOpen()
                .productName()
                .shouldBe(visible);
        productPage
                .availability()
                .shouldHave(text("In stock"));
    }
}