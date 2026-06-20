package com.ust.sdet.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;

public class DriverFactory {
    private DriverFactory(){
    }

    public static WebDriver createChromeDriver(){
        return createDriver();
    }

    public static WebDriver createDriver(){

        ChromeOptions options = new ChromeOptions();
        if(Config.headless()){
            options.addArguments(("--headless=new"));
        }
        options.addArguments("--window-size=1440,900");
        return new ChromeDriver(options);
    }
}
