package com.example.Selenide.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CartBadge {

    SelenideElement count =$("[data-test='cart-count']");



    public int count(){
        return Integer.parseInt(count.getText());
    }

    public void expectedCount(int expectedCount){
        count.shouldHave(text(String.valueOf(expectedCount)));
    }
}
