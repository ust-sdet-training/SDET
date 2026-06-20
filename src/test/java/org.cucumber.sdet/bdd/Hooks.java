package org.cucumber.sdet.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.cucumber.sdet.support.DriverFactory;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void attachScreenshotOnFailure(Scenario scenario){

        if (scenario.isFailed()
                && world.driver instanceof TakesScreenshot screenshotDriver){

            byte[] screenshot =
                    screenshotDriver.getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    "Failure Screenshot"
            );

            try {

                File source =
                        screenshotDriver.getScreenshotAs(
                                OutputType.FILE
                        );

                Path destination =
                        Paths.get(
                                "test-output",
                                scenario.getName()
                                        .replaceAll("[^a-zA-Z0-9]", "_")
                                        + ".png"
                        );

                Files.createDirectories(
                        destination.getParent()
                );

                Files.copy(
                        source.toPath(),
                        destination
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After(order = 0)
    public void tearDown(){

        if(world.driver != null){
            world.driver.quit();
        }
    }
}