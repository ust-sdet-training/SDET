package com.automation.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {

    private final World world;

    public CartSteps(World world){
        this.world = world;
    }

    @When("I click the cart")
    public void iClickTheCart() {
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} products")
    public void theCartHasProducts(int count) {
        assertEquals(count, world.cart.productCount());
    }


}
