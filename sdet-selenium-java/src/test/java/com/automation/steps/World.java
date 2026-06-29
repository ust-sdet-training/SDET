package com.automation.steps;


import com.automation.pages.AmazonHomePage;
import com.automation.pages.ProductListingPage;
import org.openqa.selenium.WebDriver;

public class World {


   AmazonHomePage amazonHomePage;
   ProductListingPage productListingPage;


    public WebDriver driver;

    public AmazonHomePage amazonHomePage(){
        return new AmazonHomePage(driver);
    }


}
