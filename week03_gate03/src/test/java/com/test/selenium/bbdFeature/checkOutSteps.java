package com.test.selenium.bbdFeature;

import com.test.selenium.pages.catalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class checkOutSteps {

    private final globalAcc world;

    public checkOutSteps(globalAcc world) {
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen() {
        world.catalog = new catalogPage(world.driver).open();
    }

    @When("I search for {string}")
    public void iSearchFor(String query) {
        world.catalog.searchFor(query);
    }

    @When("I add the first product from the search results to the cart")
    @When("I add the first product from the results to the cart")
    public void iAddTheFirstResultToTheCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }
    @Then("the cart badge should display {int}")
    @Then("the cart badge reflects {int}")
    public void theCartBadgeShows(int count) {
        world.header().cartBadge().expectedCount(count);
    }

    @When("I open the cart")
    @When("I navigate to the cart")
    public void iOpenTheCart() {
        world.cart = world.header().opencart();
    }

    @Then("the cart contains {int}")
    @Then("the cart contains {int} line items")
    @Then("the cart should contain {int} item")
    public void theCartHasLineItems(int count) {
        assertEquals(count, world.cart.lineCount());
    }

    @When("I complete the checkout process")
    @When("I proceed to checkout and place the order")
    public void iPlaceTheOrder() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is successfully confirmed")
    @Then("the order should be successfully confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(
                world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
}
