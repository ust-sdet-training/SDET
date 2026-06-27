package selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    private final SelenideElement productName = $("[data-test='detail-name']");
    private final SelenideElement addToCartButton = $("[data-test='add-to-cart']");

    public ProductPage open() {

        productName.shouldBe(visible);

        return this;
    }

    public SelenideElement avb() {
        return $("[data-testid='availability-badge']");
    }

    public SelenideElement pname() {
        return productName;
    }

}
