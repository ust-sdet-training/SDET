package org.selenium.sdet.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.selenium.sdet.support.DriverFactory;

public class Hooks {
    private final World world;

    public Hooks(World world){
        this.world=world;
    }

    @Before
    public void setUp(Scenario scenario) {
        world.scenario = scenario;
        world.driver = DriverFactory.createChromeDriver();
    }
    @After(order = 1)
    public void attackScreenshotOnFailure(Scenario scenario){
        if (scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot=screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","Failure-screenshot");
        }
    }

    @After(order = 0)
    void tearDown(){
        if(world.driver!=null){
            world.driver.quit();
        }
    }
}
