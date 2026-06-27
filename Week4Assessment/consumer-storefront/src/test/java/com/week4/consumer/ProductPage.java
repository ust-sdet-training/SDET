package com.week4.consumer;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    private final SelenideElement availabilityBadge =
            $("#availability");

    public String getAvailabilityText() {
        return availabilityBadge.getText();
    }
}