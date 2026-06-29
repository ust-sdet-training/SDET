package com.automation.pages;

import com.automation.utils.ConfigReader;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AmazonHomePage extends BasePage{

    private static final By SEARCH_INPUT = By.xpath("//input[@id='twotabsearchtextbox']");
    private static final By SEARCH_SUBMIT_BUTTON = By.cssSelector("#nav-search-submit-button");

    public AmazonHomePage(WebDriver driver) {
        super(driver);
    }

    public AmazonHomePage openAmazon(){
        driver.get(ConfigReader.getConfigValue("amazon.url"));
        visible(SEARCH_INPUT);
        return this;
    }

    public AmazonHomePage searchProduct(String productName){
        type(SEARCH_INPUT,productName);
        return this;
    }

    public ProductListingPage clickSearchSubmitButton(){
        click(SEARCH_SUBMIT_BUTTON);
        return new ProductListingPage(driver);
    }


}
