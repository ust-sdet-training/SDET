package week3.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();

        if (Config.headless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1440,900");
        options.setExperimentalOption(
                "prefs",
                Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false
                )
        );
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--incognito");
//        if (Config.gridEnabled()) {
//            try {
//                return new RemoteWebDriver(
//                        URI.create(Config.gridUrl()).toURL(),
//                        options
//                );
//            } catch (MalformedURLException e) {
//                throw new IllegalArgumentException(
//                        "Invalid Selenium Grid URL: " + Config.gridUrl(), e
//                );
//            }
//        }

        return new ChromeDriver(options);
    }
}