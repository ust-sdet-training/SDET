package com.ust.sdet.tests;

import com.ust.sdet.pages.Homepage;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class InterimTest {

    private WebDriver driver;


    @BeforeEach
    void setup(){
        driver = DriverFactory.createChromeDriver();
    }

    @AfterEach
    void cleanup(){
        if(driver!=null)
            driver.quit();
    }

    @Test
    @DisplayName("Search Hotel with Valid City and Dates")
    void searchHotel(){
        Homepage homepage = new Homepage(driver)
                .open().search();
    }
}
