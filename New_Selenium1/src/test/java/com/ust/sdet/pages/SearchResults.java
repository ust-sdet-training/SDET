package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResults extends BasePage {

    public SearchResults(WebDriver driver) {
        super(driver);
    }

    By firstProduct = By.xpath("//*[@id=\"bf04b896-db1e-4fd1-9f0c-2b8b29c58a8e\"]/div/div/div/div/span/div/div");
    By secondProduct = By.xpath("//*[@id=\"4af4d12d-b9e9-42e1-b886-ccfe7572acfd\"]/div/div/div/div/span/div/div");

    public void openFirst() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(firstProduct));

        driver.findElement(firstProduct).click();
    }

    public void openSecond() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(secondProduct));

        driver.findElement(secondProduct).click();
    }
}