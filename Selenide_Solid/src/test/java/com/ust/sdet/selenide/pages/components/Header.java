package com.ust.sdet.selenide.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Header {

    private final SelenideElement cartIcon =
            $("[data-test='cart-icon']");

    public CartBadge cartBadge() {
        return new CartBadge();
    }


    public Login loginComponent() {
        return new Login();
    }

    public Login logoutComponent() {
        return new Login();
    }
}