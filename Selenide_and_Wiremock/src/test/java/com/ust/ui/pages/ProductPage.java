package com.ust.ui.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ProductPage {

    private static final SelenideElement PRODUCTTITLE = $("#product-title");
    private static final SelenideElement ADDTOCART = $("[data-test='add-to-cart']");
    private static final SelenideElement AVAILABILITY = $("[data-testid='availability-badge']");

    public ProductPage() {
        PRODUCTTITLE.shouldBe(visible);
    }

    public ProductPage verifyPage(String available) {
        PRODUCTTITLE.shouldBe(visible);
        ADDTOCART.shouldBe(enabled);
        AVAILABILITY.shouldBe(visible).shouldHave(exactText(available));
        AVAILABILITY.shouldNotHave(exactText("Out of stock"));
        return this;
    }
}

