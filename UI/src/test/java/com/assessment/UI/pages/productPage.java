package com.assessment.UI.pages;

import com.assessment.UI.Base.basePage;
import org.openqa.selenium.By;

public class productPage extends basePage {


    private By productName = By.cssSelector("h1.pdp-title");


    private By wishlistButton = By.cssSelector("div.pdp-add-to-wishlist");


    private By loginPopup = By.cssSelector(".signInContainer");

    public String getProductName() {
        return getText(productName);
    }

    public void clickWishlist() {
        click(wishlistButton);
    }

    public boolean isLoginPopupDisplayed() {
        return isDisplayed(loginPopup);
    }
}