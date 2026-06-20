package com.week03_gate3_muhammedali.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.week03_gate3_muhammedali.pages.CatalogPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestStep {
    private final World world;
    public TestStep(World world){
        this.world=world;
    }

    @Given("the catalog is open")
    @Then("I go to the product page")
    public void openCalatalogPage(){
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I search for {string}")
    public void iSearchFor(String query){
       world.catalog.searchFor(query);
    }

    @When("I add the first result to the cart")
    @When("I try to add first product in the list - it is giving error")
    public void iAddFirstResultToCart(){
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }


    @Then("the cart badge shows {int}")
    public void cartBadgeShows(int num){
        world.header().cartBadge().expectCount(num);
    }

    @When("I open the cart")
    public void iOpenTheCart(){
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line item")
    @Then("the cart has {int} line items")
    public void cartLineCount(int count){
       assertEquals(count,  world.cart.lineCount());
    }

    @When("I place the order")
    public void iPlaceTheOrder(){
       world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theorderIsConfirmed(){
        world.checkout.confirmationText();
    }
}