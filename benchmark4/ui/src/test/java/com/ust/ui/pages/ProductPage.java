package com.ust.ui.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProductPage {

    public SelenideElement availabilityBadge() {
        open("http://localhost:5173/product/running-shoes");
        return
                $("[data-testid='availability-badge']");
    }
}