package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {
    private static WebDriver driver;
    private static final Properties config = new Properties();

    public static WebDriver initDriver() {
        if (driver != null) {
            return driver;
        }

        loadConfig();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getImplicitWait()));
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static String getBaseUrl() {
        loadConfig();
        return config.getProperty("base.url", "https://www.purplle.com");
    }

    private static int getImplicitWait() {
        loadConfig();
        try {
            return Integer.parseInt(config.getProperty("implicit.wait", "10"));
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    private static void loadConfig() {
        if (!config.isEmpty()) {
            return;
        }

        try (InputStream input = DriverFactory.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input != null) {
                config.load(input);
            }
        } catch (IOException ignored) {
        }
    }
}
