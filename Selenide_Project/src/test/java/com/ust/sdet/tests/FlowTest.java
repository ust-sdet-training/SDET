package com.ust.sdet.tests;


import com.ust.sdet.pages.*;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.page;

public class FlowTest {

    @Test
    void placeOrderFlow() {

        CatalogPage catalog = page(CatalogPage.class);

        catalog
                .openCatalogPage();

        catalog.heading()
                .shouldBe(visible)
                .shouldHave(text("Product Catalog"));

        ProductPage product = catalog
                .searchFor("Headphones")
                .selectCategory("Electronics")
                .sortBy("Price: Low to High")
                .clickSearch()
                .clickFirstProduct();

        product.title()
                .shouldBe(visible)
                .shouldHave(text("Noise Canceling Headphones"));

        product
                .availabilityBadge()
                .shouldHave(text("In stock"));

        CartPage cart = product
                .addToCart();

        cart.cartTitle()
                .shouldBe(visible)
                .shouldHave(text("Cart"));

        cart.cartBadge()
                .shouldBe(visible)
                .shouldHave(text("1"));

        cart
                .cartItems()
                .shouldHave(sizeGreaterThan(0));

        CheckoutPage checkout = cart.proceedToCheckout();

        checkout
                .checkoutTitle()
                .shouldBe(visible)
                .shouldHave(text("Checkout"));

        checkout
                .placeOrder();

        OrdersPage orders = checkout.viewOrders();

        orders
                .ordersTitle()
                .shouldBe(visible);

        orders
                .searchOrder("Noise Canceling Headphones");

        orders
                .getOrdersCount()
                .shouldHave(sizeGreaterThan(0));
    }
}
