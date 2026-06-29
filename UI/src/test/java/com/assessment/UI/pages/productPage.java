package com.assessment.UI.pages;

import com.assessment.UI.Base.basePage;
import org.openqa.selenium.By;

public class productPage extends basePage {

    private By productName = By.cssSelector(".results-base li.product-base:first-child");

    private By wishlistButton = By.cssSelector("li.product-base:first-child .product-wishlistContainer button");

    private By loginPopup = By.cssSelector(".myntraweb-splash-loginModal-layout");

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