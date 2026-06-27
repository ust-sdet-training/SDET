package com.week4.gate4.Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class productPage {


    private final SelenideElement productTitle =
            $("[data-test='detail-name']");

    private final SelenideElement productPrice =
            $(".price");

    private final SelenideElement productDescription =
            $(".lead");

    private final SelenideElement productRating =
            $("[data-test='product-rating']");

    private final SelenideElement productStock =
            $("[data-test='product-stock']");

    private final SelenideElement addToCartButton =
            $("[data-test='add-to-cart']");

    private final SelenideElement backToCatalogButton =
            $("[data-test='back-to-catalog']");
    private final SelenideElement productAvailability =
            $("[data-testid='availability-badge']");



    public SelenideElement title() {
        return productTitle;
    }

    public SelenideElement price() {
        return productPrice;
    }

    public SelenideElement description() {
        return productDescription;
    }

    public SelenideElement rating() {
        return productRating;
    }

    public SelenideElement stock() {
        return productStock;
    }
    public productPage verifyInStock(String expectedStock) {

        productAvailability.shouldBe(visible)
                .shouldHave(text(expectedStock));

        return this;
    }


    public cartPage addToCart() {

        addToCartButton
                .shouldBe(enabled)
                .click();

        return page(cartPage.class);
    }
}