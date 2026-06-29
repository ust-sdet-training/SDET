package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResults extends BasePage{

    public static WebDriver driver;
    public SearchResults(WebDriver driver){
        super(driver);
    }

    public final By secondProduct = By.xpath("//*[@id=\"anonCarousel3\"]/ol/li[2]/div/div/div[2]/div/a[1]");
    public final By firstProduct = By.xpath("//*[@id=\"anonCarousel3\"]/ol/li[1]/div/div/div[2]/div/a[1]");


    public void openFirst(){
        driver.findElement(firstProduct).click();
    }

    public void openSecond(){
        driver.findElement(secondProduct).click();
    }
}
