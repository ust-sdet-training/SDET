package com.assessment.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CatalogPage {

    private final SelenideElement catalogSearch = $("#search-products");

    private final SelenideElement firstProductCard = $("[data-test='product-card']");

    private final SelenideElement viewButton = $("a.button.primary");
    
    public CatalogPage openCatalogPage() {
        Selenide.open("/catalog");
        return this;
    }

    public CatalogPage searchFor(String keyword) {
        catalogSearch.shouldBe(visible).clear();
        catalogSearch.setValue(keyword).pressEnter();
        return this;
    }

    public CatalogPage verifySearchResultsDisplayed() {
        firstProductCard.shouldBe(visible);
        return this;
    }

    public ProductPage openFirstProduct() {
        viewButton.shouldBe(visible).click();
        return new ProductPage();
    }
}
