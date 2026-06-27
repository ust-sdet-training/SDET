package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {

    private final SelenideElement availability =
            $("dd[data-testid='availability-badge']");

    public ProductPage verifyProductIsInStock() {

        availability.shouldBe(visible)
                .shouldHave(exactText("In stock"));

        return this;
    }
}