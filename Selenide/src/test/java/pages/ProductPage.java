package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPage {
    private final SelenideElement productTitle = $("[data-test='detail-name']");
    private final SelenideElement productStock = $("[data-testid='availability-badge']");
    private final SelenideElement addToCart = $("[data-test='add-to-cart']");


    public ProductPage openPage(String product) {
        open("/product/" + product);
        productTitle.shouldBe(visible);
        return this;
    }

    public ProductPage verifyTitle(String expectedTitle) {
        productTitle.shouldHave(text(expectedTitle));
        return this;
    }

    public ProductPage verifyInStock(String expectedStock) {
        productStock.shouldBe(visible).shouldHave(text(expectedStock));
        return this;
    }

    public ProductPage verifyAddToCart() {
        addToCart.shouldBe(visible);
        return this;
    }


    public CartPage addToCart() {
        addToCart.click();
        return page(CartPage.class);
    }
}
