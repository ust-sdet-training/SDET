package org.cucumber.sdet.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("the shopper opens the catalog")
    public void theShopperOpensTheCatalog() {
        world.catalog = new org.cucumber.sdet.pages.CatalogPage(world.driver).open();
    }

    @When("the shopper searches for {string}")
    public void theShopperSearchesFor(String product) {
        world.catalog.searchFor(product);
    }

    @When("adds the product to the cart")
    public void addsTheProductToTheCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCartNew();
    }

    @When("adds {string} to the cart")
    public void addsProductToTheCart(String product) {
        world.catalog.searchFor(product);
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCartNew();
    }

    @When("the shopper opens the cart")
    public void theShopperOpensTheCart() {

        world.cart =
                world.header()
                        .openCart();
    }

    @Then("the cart contains {int} line item")
    public void theCartContainsLineItem(int expectedCount) {
        assertEquals(expectedCount, world.cart.lineCount());
    }

    @Then("the fresh cart is empty")
    public void theFreshCartIsEmpty() {
        assertEquals(0, world.cart.lineCount());
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int expectedCount) {
        world.header().cartBadge().expectCount(expectedCount);
    }

    @When("the shopper captures the cart total and completes checkout")
    public void theShopperCapturesTheCartTotalAndCompletesCheckout() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {
        String confirmation = world.checkout.confirmationText();
        assertFalse(confirmation.isBlank(), "Order confirmation message was not displayed");
    }
}