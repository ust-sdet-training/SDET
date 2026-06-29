package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage extends BasePage{

    private final By location = By.xpath("//*[@id=':Rklarct']");
    public Homepage(WebDriver driver) {
        super(driver);
    }

    public Homepage open(){
        makeconnection();
        return this;
    }

    public Homepage search(){
        visible(location);
        click(location);
        return this;

    }

}
