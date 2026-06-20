package com.ust.sdet.bdd;


import com.ust.sdet.pages.CatalogPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckoutSteps {
    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("I go the Catalog page")
    public void iGoToTheCatalogPage(){
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I opened a Product {string}")
    public void iSearchAProduct(String product){
        world.catalog.searchFor(product);

        List<String> titles= world.catalog.titles();



        System.out.println(titles);
    }

    @And("I add a product in Cart")
    public void addTheProductToTheCart(){
        world.product = world.catalog.OpenFirstProduct();
        world.cart = world.product.addToCart();
    }

    @Then("The CartBadge shows {int}")
    public void theCartBadgeShowsCount(int count){
        int expectedCountFromBadge = world.header().cartBadge().count();
       assertTrue(expectedCountFromBadge==count);
    }




    @When("I open a Cart")
    public void openTheCart(){
        world.cart =world.header().opencart();
    }

    @Then("It shows the cart line {int}")
    @Then("It shows cart lines {int}")
    public void cartLinesshowing(int count){
      assertEquals(world.cart.lineCount(),count);
    }

    @When("I placed the Order")
    public void placedTheOrder(){
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("It shows Confirmed message")
    public void confirmationMessage(){
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }
}
