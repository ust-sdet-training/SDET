package com.sdet.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver){
        super(driver);
    }

    private static final By modal = By.cssSelector(".a5c71b0007");
    private static final By dialogClose = By.cssSelector("button.de576f5064.b46cd7aad7.e26a59bb37.c295306d66.c7a901b0e7.daf5d4cb1c.e7f2b1a356");
    private static final By dateBox = By.cssSelector("[data-testid='searchbox-dates-container']");
    private static final By searchBtn = By.cssSelector("button.de576f5064.b46cd7aad7.ced67027e5.dda427e6b5.f3e59d528f.ca8e0b9533.cfd71fb584.a9d40b8d51");
    private static final By destInput = By.cssSelector("input[placeholder='Where are you going?']");

    public HomePage dialogClose(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
        driver.findElement(dialogClose).click();
        return this;
    }

    public HomePage destInput(String text) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(destInput));
        searchInput.clear();
        searchInput.sendKeys(text);
        return this;
    }

    public HomePage setStartDate(String text){
        By startDate = By.cssSelector("[data-date='"+text+"']");
        WebElement dateInputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(dateBox));
        dateInputBox.click();
        WebElement startDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(startDate));
        startDateInput.click();

        return this;
    }


    public HomePage setEndDate(String text){
        By endDate = By.cssSelector("[data-date='"+text+"']");
        WebElement endDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(endDate));
        endDateInput.click();

        return this;
    }

    public ResultsPage search(){
        WebElement search = visible(searchBtn);
        search.click();
        return new ResultsPage(driver);
    }


}
