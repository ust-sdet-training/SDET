package com.ust.sdet.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import com.ust.sdet.selenide.pages.components.Header;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    private final SelenideElement name =
            $("[data-test='detail-name']");

    private final SelenideElement addToCart =
            $("[data-test='add-to-cart']");

    private final SelenideElement availabilityBadge =
            $("[data-testid='availability-badge']");
    private final SelenideElement price = $(".price");

    public ProductPage() {
        name.shouldBe(visible);
    }

    public String name() {
        return name.text();
    }



    public int price() {
        return Integer.parseInt(
                price.shouldBe(visible)
                        .text()
                        .replaceAll("[^0-9]", "")
        );

    }


    public String availabilityBadge() {
        return availabilityBadge.shouldBe(visible).text();
    }

    public boolean isAvailabilityBadgeDisplayed() {
        return availabilityBadge.isDisplayed();
    }



    public Header header() {
        return new Header();
    }
}