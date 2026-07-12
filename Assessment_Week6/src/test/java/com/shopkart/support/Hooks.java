package com.shopkart.support;

import com.codeborne.selenide.Screenshots;
import com.shopkart.Config.AppConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import io.cucumber.java.Scenario;
import com.codeborne.selenide.*;



public class Hooks {

    private final World world;


    public Hooks(World world) {
        this.world = world;
    }

     @Before
     public void setup(){
         AppConfig.apply();
    }

    @After(order = 1)
    public void attachScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed())
            Screenshots.takeScreenShotAsFile();
    }

    @After(order =0)
    public void cleanup(){
        Selenide.closeWebDriver();
    }

}
