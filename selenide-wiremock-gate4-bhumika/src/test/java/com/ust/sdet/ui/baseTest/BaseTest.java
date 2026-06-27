package com.ust.sdet.ui.baseTest;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

        @BeforeMethod
        public void setup() {

            Configuration.baseUrl = "http://localhost:5173";

            Configuration.browser = "chrome";

            Configuration.browserSize = "1920x1080";

            Configuration.timeout = 10000;




        }

    }

