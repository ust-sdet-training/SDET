package com.assessment.ui.base;

import com.assessment.ui.utils.ConfigReader;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected ConfigReader configReader;

    @BeforeEach
    void setUp() {
        configReader = new ConfigReader();
        Configuration.baseUrl = configReader.getBaseUrl();
        Configuration.browserSize = "1280x900";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.headless = configReader.isHeadless();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}
