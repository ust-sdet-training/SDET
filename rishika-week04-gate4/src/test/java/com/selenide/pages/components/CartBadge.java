package com.selenide.pages.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartBadge {

    public int count() {

        return Integer.parseInt(
                $("[data-test='cart-count']")
                        .shouldBe(visible)
                        .getText()
        );
    }

    public void expectCount(int expectedCount) {

        $("[data-test='cart-count']")
                .shouldHave(text(String.valueOf(expectedCount)));
    }

}