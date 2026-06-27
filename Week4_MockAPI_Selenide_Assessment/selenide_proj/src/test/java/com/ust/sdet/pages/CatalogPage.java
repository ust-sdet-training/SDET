package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CatalogPage {

    private SelenideElement search = $("#search-products");
    private SelenideElement searchbtn = $("[data-test='search-button']");
    private ElementsCollection viewbtns = $$("a.button.primary");


    public CatalogPage searchFor(String product) {
        search.shouldBe(visible).setValue(product).pressEnter();
        return this;
    }

    public CatalogPage clickSearch() {
        searchbtn.click();
        return this;
    }

    public ProductPage firstProduct() {

        viewbtns.first().shouldBe(visible).click();

        return page(ProductPage.class);
    }
}
