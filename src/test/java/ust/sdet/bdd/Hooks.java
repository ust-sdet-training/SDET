package ust.sdet.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ust.sdet.support.DriverFactory;

public class Hooks {
    private final World world;

    public Hooks(World world){
        this.world = world;
    }

    @Before
    public void setUp(){
        world.driver = DriverFactory.createChromeDriver();
    }


    @After(order = 1)
    public void attachScreenshotFailure(Scenario scenario) {
        if (scenario.isFailed() && world.driver instanceof TakesScreenshot) {

            TakesScreenshot screenshotDriver = (TakesScreenshot) world.driver;
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }
    }


    @After(order=0)
    public void teardown(){
        if(world.driver != null)
            world.driver.quit();
    }

}