package com.ust.sdet.Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    private final SelenideElement title = $("#product-title");
    private final SelenideElement addToCart = $("[data-test='add-to-cart']");
    private final SelenideElement availability = $("[data-testid='availability-badge']");

    public ProductPage verifyProduct(String status) {

        title.shouldBe(visible);
        addToCart.shouldBe(enabled);
        availability.shouldHave(exactText(status));

        return this;
    }
}