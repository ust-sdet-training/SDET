package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.xpath("//input[@id='email']");
    private static final By PASSWORD_INPUT = By.xpath("//input[@id='password']");
    private static final By SIGN_IN = By.xpath("//button[text()='Sign in']");

    protected LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUserNameAndPassword(String email,String password){
        type(EMAIL_INPUT, ConfigReader.getConfigValue(email));
        type(PASSWORD_INPUT,ConfigReader.getConfigValue(password));
        return this;
    }

    public HomePage clickSignIn(){
        click(SIGN_IN);
        return new HomePage(driver);
    }

    public void waitForSignInEnable() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN));
    }
}
