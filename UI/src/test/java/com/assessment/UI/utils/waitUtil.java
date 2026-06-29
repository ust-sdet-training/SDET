package com.assessment.UI.utils;

import com.assessment.UI.Base.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class waitUtil {

    private WebDriverWait wait;

    public waitUtil() {

        wait = new WebDriverWait(
                driverFactory.getDriver(),
                Duration.ofSeconds(15)
        );

    }

    public WebElement waitForVisibility(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

    }

    public WebElement waitForClickable(By locator) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

    }

    public boolean waitForInvisibility(By locator) {

        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );

    }

    public void waitForTitle(String title) {

        wait.until(
                ExpectedConditions.titleContains(title)
        );

    }

    public void waitForURL(String url) {

        wait.until(
                ExpectedConditions.urlContains(url)
        );

    }

}