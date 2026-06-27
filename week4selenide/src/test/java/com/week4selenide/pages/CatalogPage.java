package com.week4selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    SelenideElement search = $("[data-test='search-input']");
    SelenideElement result = $("[data-test='catalog-result-count']");
    ElementsCollection productcard = $$("[data-test='product-card'], a");
    String title = "[data-test='product-title']";
    ElementsCollection titles = $$("[data-test='product-title']");
    SelenideElement FIRST_PRODUCT_BUTTON = $("[data-test='product-card'] a");
    

    public CatalogPage openPage() {
      open("/catalog");
        return this;
    }

    public CatalogPage verifyResult(String expected) {
        result.shouldHave(text(expected));
        return this;
    }

    public CatalogPage searchProduct(String value) {
        search.setValue(value).pressEnter();
        return this;
    }

    public ProductPage openFirstProduct(){
        FIRST_PRODUCT_BUTTON.click();
        return new ProductPage();
    }
}
