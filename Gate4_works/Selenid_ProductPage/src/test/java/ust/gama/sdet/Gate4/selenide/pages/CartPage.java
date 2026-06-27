package ust.gama.sdet.Gate4.selenide.pages;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    private final String cartBadge = "[data-test='cart-count']";

    public CartPage checkCartCount(String count) {
        $(cartBadge).shouldBe(visible).shouldHave(exactText(count));
        return this;
    }
}
