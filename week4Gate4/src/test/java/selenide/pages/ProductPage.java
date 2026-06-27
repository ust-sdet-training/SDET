package selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {
    private static final SelenideElement productName = $("[data-test='detail-name']");
    private static final SelenideElement availabilityBadge = $("[data-testid='availability-badge']");

    public ProductPage nameIsVisible(){
        productName.shouldBe(visible);
        return this;
    }

    public SelenideElement getAvailability(){
        return availabilityBadge;
    }

}
