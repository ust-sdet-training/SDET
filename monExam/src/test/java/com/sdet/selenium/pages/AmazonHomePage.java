package com.sdet.selenium.pages;

import com.sdet.selenium.pages.components.SearchHeader;
import com.sdet.selenium.support.Config;
import org.openqa.selenium.WebDriver;

public class AmazonHomePage extends BasePage {
    private final SearchHeader header;

    public AmazonHomePage(WebDriver driver) {
        super(driver);
        this.header = new SearchHeader(driver);
    }

    public void open() {
        driver.get(Config.baseUrl());
        header.waitUntilVisible();
    }

    public void searchFor(String keyword) {
        header.searchFor(keyword);
    }
}
