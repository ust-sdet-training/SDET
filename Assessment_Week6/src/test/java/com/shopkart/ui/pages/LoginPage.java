package com.shopkart.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.shopkart.Config.AppConfig;
import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends Xp {

    public LoginPage goToLoginPage(){
        open(AppConfig.homeUrl());
        goToLogin().shouldBe(Condition.visible)
                .click();

        return this;
    }

    public CatalogPage makeALogin(String email,String password){
        email().setValue(email);
        password().setValue(password);
        login().click();

        return new CatalogPage();
    }
}
