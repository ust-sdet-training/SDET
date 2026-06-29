package com.assessment.UI.pages;

import com.assessment.UI.Base.basePage;
import org.openqa.selenium.By;

public class wishlistPage extends basePage {

    private By wishlistMenu = By.cssSelector("a.desktop-iconWrapper[href='/wishlist']");

    private By wishlistProduct = By.cssSelector(".wishlist-productCard");

    private By removeButton = By.cssSelector(".wishlist-productCard:first-child .wishlist-removeWishlistButton");

    public void openWishlist() {

        click(wishlistMenu);

    }

    public boolean verifyProduct(String expectedProduct) {

        return getText(wishlistProduct)
                .equalsIgnoreCase(expectedProduct);

    }

    public void removeProduct() {

        click(removeButton);

    }

    public boolean isWishlistEmpty() {

        return !isDisplayed(wishlistProduct);

    }

}