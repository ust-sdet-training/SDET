package com.sdet.selenium.tests;

import com.sdet.selenium.pages.HomePage;
import com.sdet.selenium.pages.ResultsPage;
import com.sdet.selenium.support.Config;
import com.sdet.selenium.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import javax.xml.transform.Result;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchFlowTest {

    private WebDriver driver;

    @BeforeEach
    void setUp(){
        driver = DriverFactory.createChromeDriver();
        driver.get(Config.baseUrl());
    }

    @AfterEach
    void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    @DisplayName("Search and view hotels")
    void searchHotels(){
        String destination = "Bangalore";
        HomePage home = new HomePage(driver);
        ResultsPage results = new ResultsPage(driver);

        home.dialogClose().destInput(destination).setStartDate("2026-07-06").setEndDate("2026-07-10").search();
        assertEquals(destination,results.getDestination());
//        List<WebElement> list = results.cards();
//        assertTrue(list.size()>0);
    }
}
