package com.external.exam.test;

import com.external.exam.config.DriverInit;
import com.external.exam.pages.BookPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.external.exam.config.Config.baseurl;
import static com.external.exam.config.Config.catalog;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.external.exam.pages.BookPage.*;
public class SampleTest {
    public WebDriver driver;
    public WebDriverWait wait ;
    @BeforeEach
    void setup()
    {
        driver= DriverInit.createChromeDriver();

        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @AfterEach
    void stop()
    {
        if(driver!=null)
        {
            driver.quit();;
        }
    }
        @Test
        @DisplayName("flight test")
        void opentab()
        {
            driver.get(baseurl());
            WebElement search=wait.until(ExpectedConditions.visibilityOfElementLocated(search_page));
            search.sendKeys("T-shirts");
            WebElement submit=wait.until(ExpectedConditions.visibilityOfElementLocated(submit_button));
            submit.click();
            WebElement brand=wait.until(ExpectedConditions.visibilityOfElementLocated(filter));
            brand.click();
            assertEquals(driver.getCurrentUrl(),"https://www.myntra.com/t-shirts?f=Brand%3ASeekbuylove");
            WebElement low_to_high=wait.until(ExpectedConditions.visibilityOfElementLocated(sort));
            low_to_high.click();
            WebElement radio = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("input[type='radio'][value='price_asc']")
                    ));
            radio.click();
            assertEquals(driver.getCurrentUrl(),"https://www.myntra.com/t-shirt?sort=price_asc&f=Brand%3ASeekbuylove");





        }
}
