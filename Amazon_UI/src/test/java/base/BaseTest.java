package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setup() {

        driver = DriverFactory.getDriver();

        driver.get("https://www.amazon.in");

    }

    @AfterEach
    void tearDown() {

        DriverFactory.quitDriver();

    }

}