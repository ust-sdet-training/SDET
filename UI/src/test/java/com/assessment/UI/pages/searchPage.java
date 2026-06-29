package com.assessment.UI.pages;

import com.assessment.UI.Base.basePage;
import org.openqa.selenium.By;

public class searchPage extends basePage {

    // Replace with actual locator
    private By firstProduct = By.cssSelector("li.product-base:first-child .product-product");

    public void openFirstProduct() {

        click(firstProduct);

        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
        }

    }

}