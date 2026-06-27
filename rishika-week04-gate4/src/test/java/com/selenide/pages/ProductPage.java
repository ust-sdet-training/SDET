package com.selenide.pages;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
public class ProductPage extends BasePage {
    private final SelenideElement productName = $("[data-test='detail-name']");
    private final SelenideElement addToCartButton = $("[data-test='add-to-cart']");
    private final SelenideElement availabilityBadge = $("[data-testid='availability-badge']");

    public ProductPage shouldBeOpen() {
        productName.shouldBe(visible);
        return this;
    }
    public ProductPage addToCart() {
        addToCartButton.shouldBe(visible).click();
        return this;
    }
    public SelenideElement availability() {
        return availabilityBadge;
    }
    public SelenideElement productName() {
        return productName;
    }
}