package com.shopkart.ui.pages;

import com.shopkart.ui.locators.Xp;

public class CheckoutPage extends Xp {

    public CheckoutPage placeTheOrder() {
        enterAddress().setValue("UST-Trivandrum Kerala");
        placeOrder().click();
        return this;
    }

    public String orderStatus(){
        return status().getText();
    }

    public String getTotal(){
        return total().getText();
    }
}
