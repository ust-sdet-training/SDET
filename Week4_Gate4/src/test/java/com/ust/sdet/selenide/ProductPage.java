package com.ust.sdet.selenide;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class ProductPage {
    private final SelenideElement availabilityBadge = $("[data-testid='availability-badge']");

    public SelenideElement availabilityBadge() {
        return availabilityBadge;
    }
}
