package com.ust.sdet.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {


    private final SelenideElement productTitle =
            $("[data-test='detail-name']");

    private final SelenideElement addToCartButton =
            $("[data-test='add-to-cart']");

    private final SelenideElement availabilityBadge = $("[data-testid='availability-badge']");



    public SelenideElement title() {
        return productTitle;
    }

    public SelenideElement availabilityBadge() {
        return availabilityBadge;
    }


    public CartPage addToCart() {

        addToCartButton
                .shouldBe(enabled)
                .click();

        return page(CartPage.class);
    }
}