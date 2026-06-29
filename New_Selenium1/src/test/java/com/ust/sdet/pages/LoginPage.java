//package com.ust.sdet.pages;
//
//import com.ust.sdet.support.Config;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//
//public class LoginPage extends BasePage {
//
//    public LoginPage(WebDriver driver) {
//        super(driver);
//    }
//
//    private final By EMAIL = By.id("email");
//    private final By PASSWORD = By.id("password");
//    private final By SIGN_IN = By.cssSelector(".form-submit");
//
//    public void open() {
//        driver.get(Config.login());
//    }
//
//    public void login(String username, String password) {
//        driver.findElement(EMAIL).sendKeys(username);
//        driver.findElement(PASSWORD).sendKeys(password);
//        driver.findElement(SIGN_IN).click();
//    }
//}