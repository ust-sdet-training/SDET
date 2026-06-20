package com.ust.gate3.eval.bdd;

import com.ust.gate3.eval.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world){
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen(){
        world.catalog = new CatalogPage(world.driver).open();
        assertTrue(world.catalog.isThisCatalogURL().toLowerCase().contains("catalog"));
    }

    @When("I search for {string}")
    public void iSearchForProduct(String item){
        world.catalog = new CatalogPage(world.driver).open();
        world.catalog.searchFor(item);
    }

    @When("I click on the first product")
    public void iClickOnTheFirstProduct(){
        world.product = world.catalog.openFirstProduct();
    }

    @When("I update details to {int} mm, {word}, {int}, Home delivery")
    public void iUpdateTheProductDetails(int size, String color, int quantity){
        world.product.updateProductDetails(size + " mm", color, quantity, "Home delivery");
    }

    @When("I update details to {string}, {string}, {int}, {string}")
    public void iUpdateTheProductDetails(String size, String color, int quantity, String fulfilment){
        world.product.updateProductDetails(size, color, quantity, fulfilment);
    }

    @When("I add the product to the cart")
    public void iAddTheProductToCart(){
        world.cart = world.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShowsCount(int count){
        world.header().cartBadge().expectedCount(count);
    }

    @When("I open the cart")
    public void iOpenTheCart(){
        world.cart = world.header().opencart();
    }

    @Then("I see the cart has {int} line item")
    @Then("I see the cart has {int} line items")
    public void theCartHasLineItems(int lines){
        assertEquals(lines, world.cart.lineCount());
    }

    @Then("the product details are {string}, {string}, {int}")
    public void verifyProductDetailsInCart(String size, String color, int quantity){
        assertTrue(
                world.cart.verifyProductDetails(size, color, quantity)
        );
    }

    @Then("the product details are {int} mm, {word}, {int}")
    public void verifyProductDetailsInCart(int size, String color, int quantity){
        assertTrue(
                world.cart.verifyProductDetails(size + " mm", color, quantity)
        );
    }

    @When("I place the order")
    public void iPlaceTheOrder(){
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed(){
        assertTrue(world.checkout.isCheckoutPage());
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }
}
