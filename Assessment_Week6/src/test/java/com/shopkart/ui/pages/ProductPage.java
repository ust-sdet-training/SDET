package com.shopkart.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.shopkart.support.BaseUITest;
import com.shopkart.ui.locators.Xp;

public class ProductPage extends Xp {

    public CartPage addToCartWithLogin() {
        addToCart().click();
        return new CartPage();
    }
}
