package com.selenium.page;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.selenium.support.Config;

public class HomePage extends BasePage{

    private final By SOURCE       = By.id("srcinput");
    private final By DESTINATION  = By.cssSelector("input[placeholder='Enter destination city']");
    private final By DATE  = By.cssSelector(".DatePicker__DateDisplay");
    private final By SEARCH      = By.cssSelector("button[aria-label='Search buses']");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage open(){
        driver.get(Config.BASE_URL());
        return this;
    }

    public HomePage setSoruce(String source){
        type(SOURCE, source,Keys.ARROW_DOWN);
        return this;
    }

    public HomePage setDestination(String destination){
        type(DESTINATION, destination,Keys.ARROW_DOWN);
        return this;
    }

    public HomePage search(){
        click(SEARCH);
        return this;
    }



    
}


