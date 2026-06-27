package com.assessment.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class LoginPage {

   private final SelenideElement loginEmail = $("#email");

   private final SelenideElement loginPassword = $("#password");

   private final SelenideElement loginButton = $("button[type='submit']");
    
   
   public LoginPage open() {
        Selenide.open("/login");
        return this;
    }

    public LoginPage login(String username, String password) {
        loginEmail.shouldBe(visible).setValue(username);
        loginPassword.shouldBe(visible).setValue(password);
        loginButton.shouldBe(visible).click();
        return this;
    }

    public void verifyLoginSucceeded() {
        loginButton.shouldNotBe(visible);
    }
}
