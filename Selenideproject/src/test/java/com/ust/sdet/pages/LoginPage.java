package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME =
            By.cssSelector("[id='email']");

    private static final By PASSWORD =
            By.cssSelector("[id='password']");

    private static final By LOGIN_BUTTON =
            By.cssSelector("button.form-submit");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.baseUrl() + "/login");
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD, password);
        return this;
    }

    public DashboardPage clickLogin() {
        click(LOGIN_BUTTON);
        return new DashboardPage(driver);
    }

}