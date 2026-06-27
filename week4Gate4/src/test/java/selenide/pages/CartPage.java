package selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    private final SelenideElement cartBadge = $("[data-test='cart-count']");
    public CartPage verifyCartBadge(String count) {
        cartBadge.shouldBe(visible)
                .shouldHave(exactText(count));
        return this;
    }
}
