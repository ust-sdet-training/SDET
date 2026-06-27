package org.selenide.sdet.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.selenide.sdet.pages.ProductPage;
import org.selenide.sdet.support.SelenideConfig;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ProductPageTest {

    @BeforeAll
    static void setup() {
        SelenideConfig.init();
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