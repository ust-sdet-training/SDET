package week3.pages;

import week3.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By email =
            By.id("email");

    private static final By password =
            By.id("password");

    private static final By signin =
            By.cssSelector(".form-submit");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.loginUrl());
        return this;
    }

    public LoginPage email(String text) {

        WebElement emailInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(email));

        emailInput.clear();
        emailInput.sendKeys(text);
        emailInput.sendKeys(Keys.ENTER);

        return this;
    }

    public LoginPage password(String text) {

        WebElement passwordInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(password));

        passwordInput.clear();
        passwordInput.sendKeys(text);
        passwordInput.sendKeys(Keys.ENTER);

        return this;
    }

    public HomePage signIn() {
        click(signin);
        return new HomePage(driver);
    }
}
