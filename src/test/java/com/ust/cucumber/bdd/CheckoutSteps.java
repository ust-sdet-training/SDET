package com.ust.cucumber.bdd;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.ust.cucumber.pages.CatalogPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {

        private final World world;

        public CheckoutSteps(World world) {
            this.world = world;
        }

        @Given("the catalog is open")
        public void theCatalogIsOpen() {
            world.catalog = new CatalogPage(world.driver).open();

        }

        @When("I opened a product {string}")
        public void iOpenedAProduct(String product) {
            world.catalog.searchFor(product);
        }

        @And("I add a first result in cart badge")
        public void iAddTheFirstResultInCartBadge() {
            world.product = world.catalog.openFirstProduct();
            world.cart = world.product.addToCart();
        }

        @Then("the cart badge shows {int}")
        public void theCartBadgeShows(int count) {
            world.header().cartBadge().expectCount(count);
        }

        @When("I open a cart")
        @When("I opened a cart")
        public void iOpenTheCart() {

            world.cart = world.header().openCart();
        }

        @Then("the cart has {int} line item")
        @Then("the cart has {int} line items")
        public void theCartHasLineItems(int count) {

            assertEquals(count, world.cart.lineCount());
        }

        @Then("It shows the cart line {int}")
        public void itShowsTheCartLine(int count) {

            assertEquals(count, world.cart.lineCount());
        }

        @When("I place the order")
        @When("I place an order")
        public void iPlaceTheOrder() {

            String total = world.cart.total();
            ExtentCucumberAdapter.addTestStepLog("Cart Total: " + total);

            world.checkout = world.cart.proceed();

            world.checkout.placeOrder();
        }

        @Then("the order is confirmed")
        @Then("It shows the message Confirmed")
        public void theOrderIsConfirmed() {
            assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
        }
    }
