package com.week03_gate3_muhammedali.support;
 
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
 
public class DriverFactory {

    private DriverFactory() {}


    public static WebDriver createDriver(){
        if(Config.DriverName().equals("chrome")){
            return createChromeDriver();
        }else if (Config.DriverName().equals("edge")) {
            return createEdgeDriver();
        }else {
            return createChromeDriver();
        }
    }

    public static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);

        prefs.put("profile.password_manager_enabled", false);

        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        

        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1440,900");
        
        return new ChromeDriver(options);
    }

    public static WebDriver createEdgeDriver(){
        EdgeOptions options = new EdgeOptions();
        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1440,900");
        
        return new EdgeDriver(options);
    }
}
 