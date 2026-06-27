package com.example.Selenide.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    SelenideElement heading = $("#login-title");
    SelenideElement email = $("#email");
    SelenideElement password = $("#password");
    SelenideElement sign_in = $(".button.primary.form-submit");

    public HomePage makeLogin(String Email,String Password){
        heading.shouldBe(visible);
        email.setValue(Email).pressEnter();
        password.setValue(Password).pressEnter();
        sign_in.click();

        return new HomePage();
    }
}
