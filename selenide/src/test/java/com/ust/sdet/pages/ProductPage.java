package com.ust.sdet.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProductPage {
    private static final SelenideElement NAME = $("[data-test = 'detail-name']");
    private static final SelenideElement ADD = $("[data-test = 'add-to-cart']");

    public ProductPage() {
        NAME.shouldBe(visible);
    }

    public String name() {
        return NAME.getText();
    }

    public CartPage addToCart() {
        ADD.click();
        return new CartPage();
    }
}
