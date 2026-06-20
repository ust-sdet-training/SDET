package com.ust.sdet.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world) {

        this.world = world;
    }

    @Given("I open the catalog page")
    public void openCatalog() {

        world.catalog.open();
    }

    @When("I search for {string}")
    public void search(String product) {

        world.catalog.searchFor(product);
    }

    @When("I open the first product")
    public void openFirstProduct() {

        world.product =
                world.catalog.openFirstProduct();
    }

    @When("I add the product to my cart")
    public void addToCart() {

        world.product.addToCart();
    }

    @When("I open the cart")
    public void openCart() {

        world.cart = world.product.header().openCart();
    }

    @When("I proceed to checkout")
    public void proceedToCheckout() {

        world.checkout = world.cart.checkout();
    }

    @When("I place the order")
    public void placeOrder() {

        world.orderConfirmed =
                world.checkout.placeOrder();
    }


    @Then("no products should be displayed")
    public void noProductsShouldBeDisplayed() {

        assertEquals(
                "Showing 1 products",
                world.catalog.resultText()
        );
    }
}