package com.ust.sdet.bdd;

import com.ust.sdet.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
   private final World world;

    public Hooks(World world) {
        this.world = world;
    }

    @Before
    public void setup(){
        world.driver = DriverFactory.ChromeDriver();
    }

    @After (order = 1)
    public void takeAScreenshotIfFailure(Scenario scenario){
        if(scenario.isFailed() && world.driver instanceof TakesScreenshot tss){
            byte[] screenshot= tss.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","failure-screenshot");
        }
    }

    @After (order = 0)
    public void cleanup(){
        if(world.driver!=null){
            world.driver.quit();
        }
    }
}
