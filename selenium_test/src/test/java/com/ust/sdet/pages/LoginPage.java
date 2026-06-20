package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By EMAIL =
            By.id("email");

    private final By PASSWORD =
            By.id("password");

    private final By COUNTRY =
            By.id("country");

    private final By REMEMBER_ME =
            By.id("remember");

    private final By SIGN_IN =
            By.cssSelector("button[type='submit']");


    public LoginPage(WebDriver driver) {

        super(driver);
    }


    public LoginPage open() {

        driver.get("http://localhost:5173/login");

        return this;
    }


    public LoginPage enterEmail(String email) {

        type(EMAIL, email);

        return this;
    }


    public LoginPage enterPassword(String password) {

        type(PASSWORD, password);

        return this;
    }


    public LoginPage selectCountry(String country) {

        selectByVisibleText(COUNTRY, country);

        return this;
    }


    public LoginPage rememberMe() {

        click(REMEMBER_ME);

        return this;
    }


    public CatalogPage signIn() {

        click(SIGN_IN);

        return new CatalogPage(driver);
    }


    public CatalogPage loginAs(
            String email,
            String password,
            String country) {

        enterEmail(email);

        enterPassword(password);

        selectCountry(country);

        return signIn();
    }

}