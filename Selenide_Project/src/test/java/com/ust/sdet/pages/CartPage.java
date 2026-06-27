package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class CartPage {

    private SelenideElement cartTitle = $("#cart-title");
    private ElementsCollection cartItems = $$(".cart-items");
    private SelenideElement cartBadge = $("[data-test='cart-count']");
    private SelenideElement checkoutButton = $("[data-test='checkout-button']");

    public SelenideElement cartTitle() {
        return cartTitle;
    }

    public SelenideElement cartBadge() {
        return cartBadge;
    }

    public ElementsCollection cartItems() {
        return cartItems;
    }

    public CheckoutPage proceedToCheckout() {
        checkoutButton.click();
        return page(CheckoutPage.class);
    }
}