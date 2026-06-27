package com.week4selenide.pages;

import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

import com.week4selenide.support.Config;

public class ProductPage {
    SelenideElement ABILITYBADGE = $("[data-testid='availability-badge']");

    public ProductPage openProductPage() {
      open(Config.catalogUrl());
        return this;
    }

    public ProductPage abilityBadgeVisible() {
        ABILITYBADGE.shouldHave(visible);
        return this;
    }

}
