package com.shopkart.ui.pages;

import com.shopkart.support.BaseUITest;
import com.shopkart.ui.locators.Xp;

public class CartPage extends Xp {

  public CheckoutPage makeACheckout(){
      checkout().click();

      return new CheckoutPage();
  }
}
