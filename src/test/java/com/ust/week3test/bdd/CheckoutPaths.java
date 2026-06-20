package com.ust.week3test.bdd;

import com.ust.week3test.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutPaths {
    private final Definer definer;

    public CheckoutPaths(Definer definer) {
        this.definer = definer;
    }

    @Given("The catalog is open")
    public void theCatalogIsOpen() {
        definer.catalog = new CatalogPage(definer.driver).open();
    }

    @When("I search for {string}")
    public void iSearchFor(String query) {
        definer.catalog.searchFor(query);
    }

    @When("I add the first result to the cart")
    public void iAddTheFirstResultToTheCart() {
        definer.product = definer.catalog.openFirstProduct();
        definer.cart = definer.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int count) {
        definer.header().cartBadge().expectedCount(count);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        definer.cart = definer.header().openCart();
    }

    @Then("The cart has {int} line item")
    public void theCartHasLineItems(int count) {
        assertEquals(count, definer.cart.lineCount());
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        definer.checkout = definer.cart.proceed();
        definer.checkout.placeOrder();
    }

    @Then("The order is confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(definer.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }
}
