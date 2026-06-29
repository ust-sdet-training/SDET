package com.ust.sdet.tests;

import com.ust.sdet.pages.SearchPage;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class MainTest {

      static WebDriver driver;

    @BeforeAll
    static void setup(){

            driver = DriverFactory.createChromeDriver();

    }

    @AfterAll
    static void cleanup(){

        if(driver!=null){
            driver.quit();
        }

    }

    @Test
    void VerifyHappyPath(){

        SearchPage searchPage = new SearchPage(driver);
        searchPage.open();
        searchPage.searchBus("Chennai","Coimbatore","30-06-2026");

        searchPage.checkBusDetails();

    }
}
