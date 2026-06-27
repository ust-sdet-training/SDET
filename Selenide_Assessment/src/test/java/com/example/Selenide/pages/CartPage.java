package com.example.Selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    SelenideElement count = $("[data-test='cart-count']");
    SelenideElement proceedToCheckout = $(".button.button.primary");

    public void verifycount(String expectedcount){
        count.shouldHave(text(expectedcount));
    }


    public CheckoutPage proceed(){

        count.shouldHave(text("1"));
        proceedToCheckout.click();
        return new CheckoutPage();
    }

}
