package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.LoginPage;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlowSteps {

    private final World world;

    public FlowSteps(World world) {
        this.world = world;
    }


    @Given("the login page is open")
    public void theLoginPageIsOpen() {
        world.login = new LoginPage(world.driver).open();
    }

    @When("I login with {string} and {string}")
    public void iLoginWithAnd(String username, String password) {

        world.homePage = world.login
                .email(username)
                .password(password)
                .signIn();
    }

    @Then("the heading is displayed")
    public void theHeadingIsDisplayed() {

        assertTrue(
                world.homePage.isHeadingVisible(),
                "Home page heading should be visible"
        );
    }


    @When("I navigate to the catalog page")
    public void iNavigateToTheCatalogPage() {
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I searched for the {string}")
    public void iSearchedForThe(String product) {
        world.catalog.searchFor(product);
    }

    @When("I added the first result to the cart")
    public void iAddedTheFirstResultToTheCart() {

        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToTheCart();
    }

    @Then("the cart badge becomes {int}")
    public void theCartBadgeBecomes(int count) {

        world.header()
                .cartBadge()
                .expectCount(count);
    }


    @When("I opened the cart")
    public void iOpenedTheCart() {
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line of item")
    @Then("the cart has {int} line of items")
    public void theCartHasLineOfItems(int count) {

        assertEquals(
                count,
                world.cart.lineCount()
        );
    }

    @When("I placed the order")
    public void iPlacedTheOrder() {

        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is got confirmed")
    public void theOrderIsGotConfirmed() {

        assertTrue(
                world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
}