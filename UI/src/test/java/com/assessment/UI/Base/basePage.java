package com.assessment.UI.Base;

import com.assessment.UI.utils.waitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class basePage {

    protected WebDriver driver;
    protected waitUtil wait;

    public basePage() {
        driver = driverFactory.getDriver();
        wait = new waitUtil();
    }

    protected WebElement find(By locator) {
        return wait.waitForVisibility(locator);
    }

    protected void click(By locator) {
        wait.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.waitForVisibility(locator).getText();
    }

    protected boolean isDisplayed(By locator) {

        try {
            return wait.waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

}