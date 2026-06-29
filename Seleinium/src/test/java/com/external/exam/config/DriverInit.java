package com.external.exam.config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
public class DriverInit {
    private DriverInit()
    {

    }
    public static WebDriver createChromeDriver()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,900");
        return new ChromeDriver(options);
    }

}
