package com.week4.gate4.Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class cartPage {

    private SelenideElement cartTitle = $("#cart-title");
    private ElementsCollection cartItems = $$(".cart-items");
    private SelenideElement totalPrice = $("[data-test='cart-total']");

    public SelenideElement cartTitle() {
        return cartTitle;
    }

    public ElementsCollection cartItems() {
        return cartItems;
    }

    public SelenideElement totalPrice() {
        return totalPrice;
    }

}