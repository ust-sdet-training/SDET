package com.test.selenium.selenide;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;

public class productPage {
    private final SelenideElement availabilityBadge = $("[data-testid='availability-badge']");

    public SelenideElement availabilityBadge() {
        return availabilityBadge;
    }
}