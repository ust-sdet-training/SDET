package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    public static final By EMAIL = By.id("email");
    public static final By PASSWORD = By.id("password");
    public static final By SIGN_IN = By.cssSelector(".form-submit");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.loginUrl());
        return this;
    }

    public LoginPage email(String text) {

        WebElement emailInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));

        emailInput.clear();
        emailInput.sendKeys(text);
        emailInput.sendKeys(Keys.ENTER);
        return this;
    }

    public boolean isEmailVisible(){
        return visible(EMAIL).isEnabled();
    }

    public LoginPage password(String text) {

        WebElement passwordInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));

        passwordInput.clear();
        passwordInput.sendKeys(text);
        passwordInput.sendKeys(Keys.ENTER);

        return this;
    }

    public boolean isPasswordAccessible(){
        return visible(PASSWORD).isEnabled();
    }

    public boolean isSignInEnabled(){
        return visible(SIGN_IN).isEnabled();
    }
    public HomePage signIn() {
        click(SIGN_IN);
        return new HomePage(driver);
    }

    public HomePage isHomePageVisible(){
        return new HomePage(driver);
    }

    private static final By ERROR_MESSAGE =
            By.cssSelector(".alert-danger");

    public boolean isErrorDisplayed() {
        return visible(ERROR_MESSAGE).isDisplayed();
    }

    public String errorMessage() {
        return text(ERROR_MESSAGE);
    }

}
