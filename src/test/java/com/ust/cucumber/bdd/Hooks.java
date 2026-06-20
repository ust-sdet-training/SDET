package com.ust.cucumber.bdd;

import com.ust.cucumber.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {
        private final World world;
        public Hooks(World world){
            this.world=world;
        }
        @Before
        public void setup(){
            world.driver= DriverFactory.createChromeDriver();
        }
    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario) throws Exception {

        if (scenario.isFailed()
                && world.driver instanceof TakesScreenshot screenshotDriver) {

            byte[] screenshot =
                    screenshotDriver.getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    "failure-screenshot"
            );

            File src = screenshotDriver.getScreenshotAs(OutputType.FILE);

            Files.copy(
                    src.toPath(),
                    Path.of(
                            "screenshots/"
                                    + scenario.getName()
                                    + ".png"
                    )
            );
        }
        }
        @After(order=0)
        public void tearDown(){
            if(world.driver!=null){
                world.driver.quit();
            }
        }
    }


