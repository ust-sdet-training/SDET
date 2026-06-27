package ust.gama.sdet.Gate4.selenide.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage{

    private final String productTitle = "[data-test='detail-name']";
    private final String productStock = "[data-testid='availability-badge']";
    private final String addToCart = "[data-test='add-to-cart']";

    public ProductPage openProductPage(String product) {
        open("/product/" + product);
        $(productTitle).shouldBe(visible);
        return this;
    }
    public ProductPage productHasTitle(String expectedTitle) {
        $(productTitle).shouldHave(text(expectedTitle));
        return this;
    }
    public ProductPage productInStock(String expectedStock) {
        $(productStock).shouldNot(disappear).shouldHave(text(expectedStock));
        return this;
    }

    public CartPage addProductToCart() {
        $(addToCart).click();
        return page(CartPage.class);
    }
}
