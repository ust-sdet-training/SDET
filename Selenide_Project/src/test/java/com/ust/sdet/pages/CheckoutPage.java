package com.ust.sdet.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class CheckoutPage {

    private SelenideElement checkoutTitle = $("#checkout-title");
    private SelenideElement placeOrderButton = $("[data-test='place-order']");
    private SelenideElement viewOrdersButton = $(".button.primary");

    public SelenideElement checkoutTitle() {
        return checkoutTitle;
    }

    public CheckoutPage placeOrder() {
        placeOrderButton.click();
        return this;
    }

    public OrdersPage viewOrders() {
        viewOrdersButton.click();
        return page(OrdersPage.class);
    }
}