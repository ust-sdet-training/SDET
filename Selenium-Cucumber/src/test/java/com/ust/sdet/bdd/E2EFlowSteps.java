package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class E2EFlowSteps {
    private final World world;

    public E2EFlowSteps(World world) {
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen() {
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I search for {string}")
    public void iSearchFor(String query) {
        world.catalog.searchFor(query);
    }

    @When("I opened the cart")
    public void openTheCart() {
        world.header().openCart();
    }

    @Then("check cart is empty")
    public void isCartEmpty(int count) {
        assertEquals(0, count);
    }

    @Then("the cart has items")
    public void notEmptyCart(int count) {
        assertEquals(1, count);
    }

    @When("I add the first result to the cart")
    public void iAddTheFirstToTheCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int expected) {
        assertEquals(expected, world.header().cartBadge().count());
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

    @Then("I verified the price")
    public void totalPrice() {
        double price = world.cart.total();
        System.out.println("Price: " + price);
        assertTrue(price > 0.0, "the price should be greater than 0");
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("order created"));
    }
}
