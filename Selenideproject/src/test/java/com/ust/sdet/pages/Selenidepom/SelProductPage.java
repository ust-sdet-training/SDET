package com.ust.sdet.pages.Selenidepom;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SelProductPage {

    private static final String DETAIL_NAME = "[data-test='detail-name']";

    public String productName() {
        return $(DETAIL_NAME)
                .shouldBe(visible)
                .getText();
    }
}