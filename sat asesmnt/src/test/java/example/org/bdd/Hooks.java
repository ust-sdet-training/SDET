package example.org.bdd;

import example.org.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private final World world;
    public Hooks (World world){
        this.world = world;
    }

    @Before
    public void setUp(){
        world.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure_screenshot");
        }
    }

    @After(order= 0)
    public void testShutter(){
        if(world.driver!=null){
            world.driver.quit();
        }
    }

}
