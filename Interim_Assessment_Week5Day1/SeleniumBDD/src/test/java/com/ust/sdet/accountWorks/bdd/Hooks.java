package com.ust.sdet.accountWorks.bdd;

import com.ust.sdet.accountWorks.support.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks{
    private final WebPage webPage;

    public Hooks(WebPage webPage){
        this.webPage = webPage;
    }

    @Before
    public void setup(){
        webPage.driver = DriverFactory.createChromeDriver();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario){
        if(scenario.isFailed() && webPage.driver instanceof TakesScreenshot screenshotWorker){
            byte [] screenshot = screenshotWorker.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure_screenshots");
        }
    }

    @After(order = 0)
    public void testShutter(){
        if(webPage.driver!=null){
            webPage.driver.quit();
        }
    }


}
