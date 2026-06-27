package com.ust.sdet.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.ust.sdet.ui.baseTest.BaseTest;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage extends BaseTest {
    private final SelenideElement search = $("[data-test='search-input']");
    private final ElementsCollection cards = $$("[data-test='product-card']");
    private final ElementsCollection title = $$("[data-test='product-title']");
    private final ElementsCollection prices = $$("[data-test='product-price']");
    private final ElementsCollection clickFirstProduct = $$("a.button.primary");
    private final SelenideElement heading = $("#catalog-title");

    public void openPage() {

        open("http://localhost:5173/catalog");

        search.shouldBe(visible);

    }

    public CatalogPage search(String product) {

        search.clear();

        search.setValue(product);
search.pressEnter();
        cards.shouldHave(sizeGreaterThan(0));

        return this;
    }

    public ElementsCollection productNames() {
        return title;
    }

    public SelenideElement getHeading() {
        return heading;
    }

    public ElementsCollection pricesForProduct() {
        return prices;
    }
    public ProductPage clickFirstProduct() {

        clickFirstProduct.first()
                .shouldBe(visible)
                .click();

        return page(ProductPage.class);
    }

}
