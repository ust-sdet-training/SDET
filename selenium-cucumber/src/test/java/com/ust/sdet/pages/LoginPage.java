package com.ust.sdet.pages;

import com.ust.sdet.support.Config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By EMAIL = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By SIGN_IN = By.cssSelector("button[type='submit']");
    private static final By USER = By.cssSelector(".user-chip");



    public LoginPage(WebDriver driver){
        super(driver);
    }

    public LoginPage open(){
        driver.get(Config.baseUrl() + "/login");
        return this;
    }


    public void login(String email, String password){
        type(EMAIL, email);
        type(PASSWORD, password);
        click(SIGN_IN);
    }

    public boolean loggedIn(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(USER));
            return visible(USER).getText().contains("Customer User");
        }
        catch(Exception e){
            return false;
        }
    }

}