package com.sdet.week3gate3.BDD;


import com.sdet.week3gate3.Pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world){
        this.world = world;
    }
    @Given("the catalog is open")
    public void theCatalogIsOpen(){
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I search for {string}")
    public void iSearchFor(String query){
        world.catalog.searchFor(query);
    }

    @When("I add the first result to the cart")
    public void iAddTheFirstResultToTheCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToTheCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int count) {

        world.header().cartBadge().expectCount(count);
        assertEquals(count, world.cart.lineCount());
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line item")
    @Then("the cart has {int} line items")
    public void theCartHasLineItems(int count) {

        assertEquals(count, world.cart.lineCount());
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
        assertTrue(world.driver.getCurrentUrl().contains("checkout"),
                "Not navigated to checkout");
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
}

