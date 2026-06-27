package selenide.support;

import com.codeborne.selenide.Configuration;

public class SelenideConfig {


    static {
        Configuration.baseUrl = "http://localhost:5173";
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1440x900";
        Configuration.timeout = 10000;
    }

    public static void init() {

    }
}
