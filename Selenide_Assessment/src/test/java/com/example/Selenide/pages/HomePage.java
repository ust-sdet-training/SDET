package com.example.Selenide.pages;

import com.codeborne.selenide.SelenideElement;
import com.example.Selenide.config.TestConfig;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomePage {




    SelenideElement sign_in = $(".button.primary");
    SelenideElement user = $(".user-chip");
    SelenideElement preview = $("a.button.secondary");

    public HomePage gotohome(){
        open(TestConfig.homeUrl());
        return this;
    }

    public LoginPage gotoLogin(){
        sign_in.click();
        return new LoginPage();
    }

    public CatalogPage goToCatalogAsLoggedUser(){
       user.shouldBe(visible);
       preview.click();

       return new CatalogPage();
    }
}
