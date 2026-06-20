package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
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

    @Given("The catalog is open")
    public void theCatalogIsOpen () {
        world.catalog = new CatalogPage(world.driver).open();


    }
    @When("I search for a {string}")
    public void iSearchFor(String query){
        world.catalog.searchFor(query);
    }
    @When("I add the first result to the cart")
    public void iAddTheFirstResultToTheCart(){
        world.product = world.catalog.openFirstProduct();
        world.product.addToCart();

    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int count){
        world.header().cartBadge().expectCount(count);

    }

    @When("I open the cart")
    public void iOpenTheCart(){
        world.cart = world.header().openCart();

    }

    @Then("the cart has {int} line item")
    @Then("the cart has {int} line items")
    public void theCartHasLineItems(int count ){
        assertEquals(count,world.cart.lineCount());
    }
    @When("I place the order")
    public void iPlaceTheOrder(){
        world.checkoutPage = world.cart.proceed();
        world.confirmationPage = world.checkoutPage.placeOrder();

    }
    @Then("the order is confirmed")
    public void theOrderisConfirmed(){
        assertTrue(world.confirmationPage.confirmationText().toLowerCase().contains("confirmed"));
    }
}
