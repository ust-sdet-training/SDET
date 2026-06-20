package com.ust.sdet.bdd;

import com.ust.sdet.pages.CartPage;
import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.support.Config;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen() {

        world.driver.get(Config.baseUrl());

        world.header().openProducts();

        world.catalog = new CatalogPage(world.driver);
    }

    @When("I search for {string}")
    public void iSearchFor(String product) {

        world.catalog.search(product);
    }

    @When("I open the first matching product")
    public void iOpenTheFirstMatchingProduct() {

        world.product =
                world.catalog.openFirstMatchingProduct();
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {

        world.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int expected) {

        assertEquals(
                expected,
                world.header()
                        .cartBadge()
                        .count()
        );
    }

    @When("I open the cart")
    public void iOpenTheCart() {

        world.header().openCart();

        world.cart = new CartPage(world.driver);
    }

    @Then("the cart has {int} line item")
    public void theCartHasLineItem(int expected) {

        assertEquals(
                expected,
                world.cart.lineCount()
        );
    }

    @Then("the cart has {int} line items")
    public void theCartHasLineItems(int expected) {

        assertEquals(
                expected,
                world.cart.lineCount()
        );
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {

        world.checkout =
                world.cart.proceed();
    }

    @When("I place the order")
    public void iPlaceTheOrder() {

        world.confirmationPage =
                world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {

        assertTrue(
                world.confirmationPage
                        .confirmationMessage()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
}