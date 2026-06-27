package com.week4.gate4.tests;

import com.week4.gate4.Pages.cartPage;
import com.week4.gate4.Pages.catalogPage;
import com.week4.gate4.Pages.productPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.page;

public class BadgeTest {

    @Test
    void placeOrderFlow() {

        catalogPage catalog = page(catalogPage.class);

        productPage product = catalog
                .openCatalogPage()
                .searchFor("Headphones")
                .clickFirstProduct();

        product.title()
                .shouldBe(visible);
        product.verifyInStock("In Stock");

        cartPage cart = product.addToCart();

        cart.cartTitle()
                .shouldBe(visible);

        cart.cartItems()
                .shouldHave(sizeGreaterThan(0));


    }
}

