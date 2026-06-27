package com.ust.ui.tests;

import com.codeborne.selenide.Configuration;
import com.ust.ui.pages.ProductPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;

public class ProductUiTest {

    @BeforeAll
    static void configureBrowser() {
        Configuration.headless = true;
        Configuration.browserSize = "1366x768";
        Configuration.browserCapabilities.setCapability(
                "goog:chromeOptions",
                java.util.Map.of("args", java.util.List.of(
                        "--headless=new",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--remote-allow-origins=*"
                ))
        );
    }

    @Test
    void verifyAvailability() {
        ProductPage page = new ProductPage();
        page.availabilityBadge()
                .shouldHave(text("In stock"));
    }
}
