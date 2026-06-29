package com.sdet.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ResultsPage extends BasePage{
    public ResultsPage(WebDriver driver){
        super(driver);
    }

    private static final By resultHeader = By.cssSelector(".b87c397a13.cacb5ff522");

    private static final By destInput = By.cssSelector("input[placeholder='Where are you going?']");
    private static final By dateBox = By.cssSelector("[data-testid='searchbox-dates-container']");
    private static final By propertyCard = By.cssSelector("[data-testid='property-card']");
    public String getDestination(){
        WebElement dest = visible(destInput);
        return dest.getAttribute("value");
    }

    public List<WebElement> cards(){
        return visibleElements(propertyCard);
    }


}
