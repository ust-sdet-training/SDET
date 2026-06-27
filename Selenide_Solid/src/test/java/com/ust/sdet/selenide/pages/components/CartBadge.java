package com.ust.sdet.selenide.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CartBadge {

    private final SelenideElement count = $("[data-test='cart-count']");

    public int count() {
        return Integer.parseInt(
                count.shouldBe(visible).getText()
        );
    }

    public void expectedCount(int expectedCount) {
        count.shouldHave(exactText(String.valueOf(expectedCount)));
    }
}