package com.ust.week3test.bdd;

import com.ust.week3test.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private final Definer definer;

    public Hooks(Definer definer){
        this.definer = definer;
    }

    @Before
    public void setup(){
        definer.driver = DriverFactory.createdChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && definer.driver instanceof TakesScreenshot screenshotDriver){
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }
    }

    @After(order = 0)
    public void tearDown() {
        if(definer.driver != null){
            definer.driver.quit();
        }
    }

    @After(order = 2)
    public void cartCountAttach(Scenario scenario){
        if(definer.cart!=null){
            scenario.attach(definer.header().cartBadge().byteCount(), "text/plain", "cart-total-captured-before-navigation");
        }
    }


}
