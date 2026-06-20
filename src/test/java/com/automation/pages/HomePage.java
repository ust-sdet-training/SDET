package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{

    private static final By SIGN_IN = By.xpath("//a[text()='Sign in']");
    private static final By SIGN_OUT = By.xpath("//button[text()='Sign out']");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.getConfigValue("application.url"));
        visible(SIGN_IN);
        return this;
    }

    public LoginPage clickSignIn()  {
        click(SIGN_IN);
        wait.until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    public boolean isSignOutButtonPresent() {
        visible(SIGN_OUT);
        return true;
    }
}
