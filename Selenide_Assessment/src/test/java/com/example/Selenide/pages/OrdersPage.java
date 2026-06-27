package com.example.Selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OrdersPage {

    SelenideElement records = $("table");

    public void visibletable(){

        records.shouldBe(visible);

    }
}
