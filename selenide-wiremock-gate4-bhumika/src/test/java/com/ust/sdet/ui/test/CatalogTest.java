package com.ust.sdet.ui.test;

import com.ust.sdet.ui.baseTest.BaseTest;
import com.ust.sdet.ui.pages.CartPage;
import com.ust.sdet.ui.pages.CatalogPage;
import com.ust.sdet.ui.pages.ProductPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public class CatalogTest extends BaseTest {

    @Test
    public void searchForHeadphones() {

        CatalogPage catalog = page(CatalogPage.class);

        catalog.openPage();

        catalog.getHeading().shouldBe(visible).shouldHave(text("Product Catalog"));
        catalog.search("Travel");

        catalog.productNames().shouldHave(size(3));

        catalog.productNames().shouldHave(texts("Travel Backpack", "Resistance Bands Kit",
                                "Skin Care Travel Kit"));

        ProductPage product = catalog.clickFirstProduct();
        product.title().shouldHave(text("travel"));
        product.verifyInStock("In Stock");
        CartPage cart = product.addToCart();

        cart.verifyCartBadge("1");

    }
    }
