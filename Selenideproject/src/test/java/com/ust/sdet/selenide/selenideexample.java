package com.ust.sdet.selenide;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class selenideexample {

    @BeforeEach
    void setup() {
        open("http://localhost:5173/catalog");
    }

    @Test
    @DisplayName("Search headphones with Selenide")
    void searchForHeadphonesShowsResults() {

        $("[data-test='search-input']")
                .shouldBe(Condition.visible)
                .setValue("headphones")
                .pressEnter();

        $$("[data-test='product-card']")
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
    }
}