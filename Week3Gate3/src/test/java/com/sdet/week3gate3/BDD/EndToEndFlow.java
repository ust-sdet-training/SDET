package com.sdet.week3gate3.BDD;

import com.sdet.week3gate3.Pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class EndToEndFlow {

    private final World world;
    public EndToEndFlow(World world) {
        this.world = world;
    }

    @Given("the login page is open")
    public void theLoginPageIsOpen() {
        world.login = new LoginPage(world.driver).open();
        assertTrue(world.login.isLoginPageDisplayed());
    }

    @When("I login with {string} and {string}")
    public void login(String email, String password) {
        world.homePage = world.login.email(email).password(password).signIn();
        assertNotNull(world.homePage,
                "Home page should be loaded after login");
    }

    @Then("the heading is displayed")
    public void isHeadingDisplayed() {
        assertTrue(world.homePage.isHeadingVisible());

    }

    @When("I navigate to the catalog page")
    public void navigateToCatalog() {
        world.catalog = world.homePage.gotoCatalog();
    }

    @When("I searched for the {string}")
    public void iSearchFor(String query) {
        world.catalog.searchFor(query);

    }

    @When("I added the first result to the cart")
    public void iAddTheFirstResultToTheCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToTheCart();
    }

    @Then("the cart badge becomes {int}")
    public void theCartBadgeShows(int count) {
        world.header().cartBadge().expectCount(count);
        assertEquals(count, world.cart.lineCount());
    }

    @When("I opened the cart")
    public void iOpenTheCart() {
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line of item")
    @Then("the cart has {int} line of items")
    public void theCartHasLineItems(int count) {
        assertEquals(count, world.cart.lineCount());
    }

    @When("I placed the order")
    public void iPlaceTheOrder() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
        assertNotNull(
                world.checkout,
                "Checkout page should open"
        );
    }

    @Then("the order is got confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(
                world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );

    }
}
