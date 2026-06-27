package com.assessment.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage {

   private final SelenideElement productTitle = $("[data-test='detail-name']");

   private final SelenideElement availabilityLabel = $$("dt").findBy(com.codeborne.selenide.Condition.text("Availability"));

   private final SelenideElement availabilityValue = $("[data-testid='availability-badge']");

    public ProductPage verifyProductPageOpened() {
        productTitle.shouldBe(visible);
        return this;
    }

    public ProductPage verifyProductUrlContains(String expectedText) {
        String currentUrl = WebDriverRunner.url();
        Assertions.assertTrue(currentUrl.contains(expectedText), "Expected URL to contain: " + expectedText + " but was: " + currentUrl);
        return this;
    }

    public ProductPage verifyTitleVisible() {
        productTitle.shouldBe(visible);
        return this;
    }

    public ProductPage verifyAvailabilityLabelVisible() {
        availabilityLabel.shouldBe(visible);
        return this;
    }

    public ProductPage verifyAvailabilityValueEquals(String expectedValue) {
        availabilityValue.shouldHave(text(expectedValue));
        return this;
    }
}
