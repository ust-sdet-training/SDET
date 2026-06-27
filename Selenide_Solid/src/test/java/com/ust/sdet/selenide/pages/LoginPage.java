package com.ust.sdet.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import com.ust.sdet.selenide.support.SelenideConfig;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class LoginPage {

    private final SelenideElement email = $("#email");
    private final SelenideElement password = $("#password");
    private final SelenideElement signIn =
            $("#main-content section form button");
    private final SelenideElement loginPageTitle = $("#login-title");
    private final SelenideElement title = $("#page-title");
    private final SelenideElement signOut =
            $("#main-content section.hero div.hero-copy div button");

    public LoginPage() {
    }

    public LoginPage openSignPage() {
        open(SelenideConfig.loginUrl());
        loginPageTitle.shouldBe(visible);
        return this;
    }

    public LoginPage login(String email, String password) {
        this.email.setValue(email);
        this.password.setValue(password);
        signIn.click();

        webdriver().shouldHave(urlContaining("/home"));
        return this;
    }

}