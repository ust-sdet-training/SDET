package com.example.Selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CheckoutPage {

    SelenideElement placeorder = $("[data-test='place-order']");
    SelenideElement viewOrders = $(".button.button.primary");

    public CheckoutPage placetheOrder(){

        placeorder.click();
        return this;
    }

    public OrdersPage viewOrders(){
        viewOrders.click();
        return new OrdersPage();
    }
}
