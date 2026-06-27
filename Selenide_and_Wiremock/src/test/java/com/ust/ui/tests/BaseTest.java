package com.ust.ui.tests;

import com.codeborne.selenide.Configuration;
import com.ust.ui.support.Config;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void setup() {

        Configuration.baseUrl = Config.baseUrl();
//        Configuration.browser = Config.browser();
        Configuration.headless = Config.headless();
//        Configuration.timeout = Config.timeout();
    }
}
