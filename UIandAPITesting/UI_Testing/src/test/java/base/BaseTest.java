package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    @BeforeMethod
    public void setup() {

        driver = DriverFactory.getDriver();
        driver.get("https://www.myntra.com/");
    }
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}