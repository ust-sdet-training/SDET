package com.ust.sdet.selenide;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.ust.sdet.support.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogSelenideTest {

    private static final String SEARCH_INPUT = "[data-test='search-input']";
    private static final String RESULT_INPUT = "[data-test='catalog-result-count']";
    private static final String PRODUCT_CARD = "[data-test='product-card']";
    private static final String PRODUCT_TITLE = "[data-test='product-title']";
    private static final String PRODUCT_PRICE = "[data-test='product-price']";
    private static final String EMPTY_SEARCH = "[data-test='empty-search']";
    private static final String PRODUCT_LINK = "[data-test='product-card'] a";
    private static final String DETAIL_NAME = "[data-test='detail-name']";
    private static final String ADD_TO_CART = "[data-test='add-to-cart']";
    private static final String CART_COUNT = "[data-test='cart-count']";
    private static final String SORT_SELECT = "[data-test='sort-select']";
    private static final String QUICK_VIEW = "[data-test='quick-view']";
    private static final String SIZE_GUIDE = ".product-actions a";

    @BeforeEach
    void setup() {
        open(Config.catalogUrl());
    }

    private void search(String query, String expectedResult) {

        $(SEARCH_INPUT)
                .shouldBe(visible)
                .clear();

        $(SEARCH_INPUT)
                .setValue(query)
                .pressEnter();

        $(RESULT_INPUT)
                .shouldHave(exactText(expectedResult));
    }

    @Test
    @DisplayName("Searching a Product")
    void searching_a_product_checks_visibility() {

        search("headphones", "Showing 1 product");

        ElementsCollection cards = $$(PRODUCT_CARD);

        for (SelenideElement card : cards) {

            String title = card.$(PRODUCT_TITLE).getText();

            assertAll(
                    () -> assertTrue(title.toLowerCase().contains("headphones")),
                    () -> card.$(PRODUCT_PRICE).shouldBe(visible)
            );
        }
    }

    @Test
    @DisplayName("Searching a product which is not available")
    void searching_a_product_not_available() {

        search("no-product", "Showing 0 products");

        $(EMPTY_SEARCH).shouldBe(visible);

        assertAll(
                () -> $$(PRODUCT_CARD).shouldHave(size(0)),
                () -> assertEquals(
                        "No products found",
                        $(EMPTY_SEARCH).$("h2").getText()
                )
        );
    }

    @Test
    @DisplayName("Open the product detail")
    void open_product_detail() {

        $(PRODUCT_LINK)
                .shouldBe(visible)
                .click();

        $(DETAIL_NAME).shouldBe(visible);

        assertAll(
                () -> assertTrue(WebDriverRunner.url().contains("/product/")),
                () -> assertFalse($(DETAIL_NAME).getText().isBlank())
        );

        $(ADD_TO_CART)
                .shouldBe(enabled)
                .click();

        $(CART_COUNT)
                .shouldHave(exactText("1"));
    }

    @Test
    @DisplayName("Sort the Items")
    void sorting_the_products() {

        $$(PRODUCT_CARD)
                .shouldHave(sizeGreaterThan(0));

        $(SORT_SELECT)
                .selectOption("Price: Low to High");

        $$(PRODUCT_CARD)
                .shouldHave(sizeGreaterThan(0));

        List<Integer> prices = $$(PRODUCT_PRICE)
                .texts()
                .stream()
                .map(text -> Integer.parseInt(text.replaceAll("[^0-9]", "")))
                .toList();

        assertEquals(
                prices.stream().sorted().toList(),
                prices
        );
    }
}