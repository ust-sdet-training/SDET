package com.automation.steps;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private final World world;

    public Hooks(World world){
        this.world = world;
    }

    @Before
    public void setUp(){
        ConfigReader.loadConfig();
        world.driver = DriverManager.createDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png", scenario.getName() );
        }
    }

    @After(order = 0)
    public void tearDown(){
        if(world.driver != null)
            world.driver.quit();
    }
}
