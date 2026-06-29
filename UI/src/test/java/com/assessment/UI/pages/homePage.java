package com.assessment.UI.pages;

import com.assessment.UI.Base.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class homePage extends basePage {


    private By searchBox = By.cssSelector(".desktop-searchBar");

    public void searchProduct(String product) {

        type(searchBox, product);

        find(searchBox).sendKeys(Keys.ENTER);

    }

}