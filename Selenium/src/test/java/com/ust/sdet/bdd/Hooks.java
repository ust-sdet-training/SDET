package com.ust.sdet.bdd;

import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {

    private final World world;
    public Hooks(World world){
        this.world = world;
    }

    @BeforeAll
    public static void writeAllureEnvironment() throws IOException {
        Path results = Path.of("target/allure-results");
        Files.createDirectories(results);
        Files.writeString(
                results.resolve("environment.properties"),
                "Browser=Chrome\n" +
                        "Headless=" + System.getProperty("headless", "true") + "\n" +
                        "BaseURL=" + Config.baseUrl() + "\n"
        );
    }

    @Before
    public void setUp(Scenario scenario){
        world.scenario = scenario;
        world.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && world.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","failure-screenshot");
        }
    }
    @After(order = 0)
    public void tearDown(){
        if(world.driver != null)
            world.driver.quit();
    }

}
