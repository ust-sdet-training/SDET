package com.ust.sdet.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {


    private final SelenideElement title = $("[data-test='detail-name']");
    private final SelenideElement price = $(".price");
    private final SelenideElement descp = $(".lead");
    private final SelenideElement rating = $("[data-test='product-rating']");
    private final SelenideElement availability = $$("dl.product-meta dd").findBy(text("in stock"));



    public SelenideElement Title() {
        return title;
    }

    public SelenideElement Price() {
        return price;
    }

    public SelenideElement description() {
        return descp;
    }

    public SelenideElement Rating() {
        return rating;
    }

    public SelenideElement stock() {
        return availability;
    }

}