package com.selenium.Test;

import org.junit.jupiter.api.Test;

import com.selenium.page.HomePage;
import com.selenium.support.DriverFactory;

public class Tested {

    @Test
    void testing(){
        new HomePage(DriverFactory.createChromeDriver()).open().setSoruce("Chennai");
    }
}
