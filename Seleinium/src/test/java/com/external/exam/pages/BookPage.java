package com.external.exam.pages;

import com.external.exam.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BookPage extends BasePage
{
    public static final By search_page=By.className("desktop-searchBar");
    public static final By submit_button=By.className("desktop-submit");
    public static final By filter=By.xpath("//label[contains(., 'Seekbuylove')]");
    public static final By bn=By.className("product-brand");
    public static final By sort=By.className("sort-sortBy");
    public static final By sel=By.xpath("//input[@value='price_desc']");



    public BookPage(WebDriver driver)
    {
        super(driver);
    }
    public BookPage open()
    {

        driver.get(Config.login());
        wait.until(ExpectedConditions.visibilityOfElementLocated(search_page));
        return this;
    }


}
