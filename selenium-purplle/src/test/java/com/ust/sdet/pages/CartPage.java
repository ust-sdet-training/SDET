package com.ust.sdet.pages;
import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private static final By TOTAL = By.cssSelector("h5");
    public CartPage(WebDriver driver) {
        super(driver);
        driver.get(Config.baseUrl() + "/cart");
    }

    public String total() {
        return text(TOTAL);
    }
}
