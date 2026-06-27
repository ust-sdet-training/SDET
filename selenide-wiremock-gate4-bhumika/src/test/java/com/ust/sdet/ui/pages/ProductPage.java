package com.ust.sdet.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ProductPage {


        private final SelenideElement productTitle =
                $("[data-test='detail-name']");

    private final SelenideElement productStock =
            $("[data-testid='availability-badge']");
        private final SelenideElement addToCartButton =
                $("[data-test='add-to-cart']");

        private final SelenideElement backToCatalogButton =
                $("[data-test='back-to-catalog']");

private final SelenideElement addCart=$("[data-test='add-to-cart']");
        public SelenideElement title() {
            return productTitle;
        }

    public ProductPage verifyInStock(String expectedStock) {

        productStock.shouldBe(visible)
                .shouldHave(text(expectedStock));

        return this;
    }
    public CartPage addToCart(){
        addCart.click();
        return page(CartPage.class);
    }


    }



