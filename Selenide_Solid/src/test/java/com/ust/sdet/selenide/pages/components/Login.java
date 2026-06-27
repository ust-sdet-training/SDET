package com.ust.sdet.selenide.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class Login {

    private final SelenideElement title = $("#page-title");
    private final SelenideElement loginPageTitle = $("#login-title");

    public String getWelcomeTitle() {
        return title.shouldBe(visible).text();
    }

    public String signOut() {
        webdriver().shouldHave(urlContaining("/login"));
        return loginPageTitle.shouldBe(visible).text();
    }
}