package com.ust.gate3.eval.bdd;

import com.ust.gate3.eval.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


public class Hooks{
    private final World world;

    public Hooks(World world){
        this.world = world;
    }

    @Before
    public void setup(){
        world.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotWorker){
            byte [] screenshot = screenshotWorker.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure_screenshots");
        }
    }

    @After(order = 0)
    public void testShutter(){
        if(world.driver!=null){
            world.driver.quit();
        }
    }


}
