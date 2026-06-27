package com.ust.sdet.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    private SelenideElement heading = $("#catalog-title");
    private SelenideElement searchBox = $("#search-products");

    private ElementsCollection productCards = $$(".product-card");

    private ElementsCollection productNames = $$("[data-test='product-title']");

    private SelenideElement ProductView = $("a.button.primary");

    private SelenideElement productTitle = $("[data-test='detail-name']");

    private SelenideElement availabilityBadge = $("[data-testid='availability-badge']");




    public void openCatalogPage() {
        open("http://localhost:5173/catalog");
    }

    public SelenideElement heading() {
        return heading;
    }
    public SelenideElement ProductView() {
        return ProductView;
    }
    public SelenideElement ProductTitle() {
        return productTitle;
    }


    public String getProductMetaValue(String label) {
        return $x("//dl[@class='product-meta']//dt[text()='" + label + "']/following-sibling::dd")
                .getText();
    }

    public String getAvailabilityBadge(String label) {
        return availabilityBadge.getText();
    }


    public CatalogPage searchFor(String product) {
        searchBox.shouldBe(visible)
                .setValue(product)
                .pressEnter();
        return this;
    }

    public ElementsCollection results() {
        return productCards;
    }

    public ElementsCollection productNames() {
        return productNames;
    }

    public void clickProduct(String product){
        results().filterBy(text(product)).first().$("a.button.primary").click();
    }
}