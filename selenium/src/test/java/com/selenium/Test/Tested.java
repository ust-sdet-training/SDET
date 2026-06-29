package com.selenium.Test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.selenium.page.HomePage;
import com.selenium.support.DriverFactory;

public class Tested {
    public static WebDriver driver;

    @BeforeAll
    static void setup(){
        driver = DriverFactory.createChromeDriver();
    }

    @AfterAll
    static void terminate(){
        if(driver!=null){
            driver.quit();
        }
    }

    @Test
    void testing(){
        new HomePage(driver).open();
    }
}
