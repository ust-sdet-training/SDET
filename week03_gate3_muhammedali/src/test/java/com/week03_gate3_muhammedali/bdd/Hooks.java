package com.week03_gate3_muhammedali.bdd;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.week03_gate3_muhammedali.support.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    private final World world;
    
    public Hooks(World world){
        this.world=world;
    }

    @Before
    public void setup(){
        world.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if (scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }
    }

    @After(order = 2)
    public void attachCartCountIfCartPageIsUsed(Scenario scenario){
        if(world.cart!=null){
            scenario.attach(world.header().cartBadge().getcountByte(), "text/plain", "cart-count-in-steps");
        }
    }



    @After(order = 0)
    public void tearDown(){
        if (world.driver!=null) {
            world.driver.quit();
        }
    }

}
