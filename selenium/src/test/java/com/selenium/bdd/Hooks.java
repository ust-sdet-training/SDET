package com.selenium.bdd;

import com.selenium.support.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    private final World world;
    
    public Hooks(World world){
        this.world=world;
    }

    @Before
    public void setup(){
        world.driver = DriverFactory.createChromeDriver();
    }

    // @After(order = 1)
    // public void attachScreenshotOnFailure(Scenario scenario){
    //     if (scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
    //         byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
    //         scenario.attach(screenshot, "image/png", "failure-screenshot");
    //     }
    // }

    @After(order = 0)
    public void tearDown(){
        if (world.driver!=null) {
            world.driver.quit();
        }
    }

}
