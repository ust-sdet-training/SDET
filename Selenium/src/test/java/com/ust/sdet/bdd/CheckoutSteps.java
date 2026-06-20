package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {
    private final World world;

    public CheckoutSteps(World world) { this.world = world; }

    @Given("the shopper opens the catalog")
    public void openCatalog() { world.catalog = CatalogPage.open(world.driver); }

    @When("the shopper searches for {string}")
    public void search(String product) {
        world.selectedProduct = product;
        world.catalog.searchFor(product);
    }

    @When("adds the product to the cart")
    public void addProduct() {
        world.product = world.catalog.productNamed(world.selectedProduct).open();
        assertEquals(world.selectedProduct, world.product.productName());
        world.product.addToCart();
    }

    @When("adds {string} to the cart")
    public void addNamedProduct(String product) { search(product); addProduct(); }

    @Then("the cart badge shows {int}")
    public void badgeShows(int expected) { assertEquals(expected, world.header().cartBadge().count()); }

    @When("the shopper opens the cart")
    public void openCart() { world.cart = world.header().openCart(); }

    @Then("the cart contains {int} line item(s)")
    public void cartLineCount(int expected) { assertEquals(expected, world.cart.lineItemCount()); }

    @Then("the fresh cart is empty")
    public void freshCartEmpty() {
        assertTrue(world.cart.isEmpty(), "A new browser session should have an empty cart");
    }

    @When("the shopper captures the cart total and completes checkout")
    public void completeCheckout() {
        Allure.addAttachment(
                "Cart Total Before Checkout",
                world.cart.total()
        );
        world.cartTotal = world.cart.total();
        world.scenario.attach(world.cartTotal, "text/plain", "Cart total before checkout");
        world.checkout = world.cart.checkout().placeOrder();
    }

    @Then("the order is confirmed")
    public void orderConfirmed() { assertTrue(world.checkout.isOrderConfirmed()); }
}
