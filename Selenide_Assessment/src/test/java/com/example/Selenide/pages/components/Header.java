package com.example.Selenide.pages.components;

import com.codeborne.selenide.SelenideElement;
import com.example.Selenide.pages.CartPage;

import static com.codeborne.selenide.Selenide.$;

public class Header {

    SelenideElement cart_icon =$("[data-test='cart-icon'");


    public CartPage opencart(){
        cart_icon.click();
        return new CartPage();
    }
}
