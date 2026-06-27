package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrdersPage {

    private SelenideElement ordersTitle = $("#orders-title");
    private ElementsCollection ordersCount = $$("[data-testid='orders-count']");
    private SelenideElement searchProduct = $(".table-note");

    public SelenideElement ordersTitle() {
        return ordersTitle;
    }

    public ElementsCollection getOrdersCount() {
        return ordersCount;
    }

    public OrdersPage searchOrder(String ProductName) {
        searchProduct.shouldBe(visible).shouldHave(text(ProductName));
        return this;
    }
}