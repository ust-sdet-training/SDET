package example.org.pages;

import example.org.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final By SIGN_IN_BUTTON = By.cssSelector("a[href='/login']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.homeUrl());
        visible(SIGN_IN_BUTTON);
        return this;
    }


}
