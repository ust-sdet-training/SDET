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
    public void setUp() {
        world.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void captureFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) world.driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
    }

    @After(order = 0)
    public void tearDown() {

        if (world.driver != null) {
            world.driver.quit();
        }
    }
}