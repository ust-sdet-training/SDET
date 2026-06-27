package com.ust.sdet.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    private final SelenideElement productName =
            $("h1#product-title");

    private final SelenideElement availability =
            $("[class*='availability'], dd");

    public ProductPage verifyProductName(String expected) {

        productName.shouldBe(visible)
                .shouldHave(text(expected));

        return this;
    }

    public ProductPage verifyAvailability(String expected) {

        availability.shouldBe(visible)
                .shouldHave(text(expected));

        return this;
    }
}