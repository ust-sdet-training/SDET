package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By searchBox = By.id("twotabsearchtextbox");
    private final By searchButton = By.id("nav-search-submit-button");

    public void open() {
        driver.get("https://www.amazon.in/");
    }

    public void searchProduct(String product) {

        driver.findElement(searchBox).sendKeys(product);
        driver.findElement(searchButton).click();

    }
}