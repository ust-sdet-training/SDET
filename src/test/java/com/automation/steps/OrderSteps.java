package com.automation.steps;

import com.automation.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderSteps {

    private final World world;

    public OrderSteps(World world){
        this.world = world;
    }

    @Given("the sdet retail app is open")
    public void theSdetRetailAppIsOpen() {
        world.home = new HomePage(world.driver).open();
    }

    @When("I click on Products")
    public void iClickOnProducts() {
        world.catalog = world.header().clickProduct();
    }

    @Then("the Product Catalog page is open")
    public void theProductCatalogPageIsOpen() {
        world.catalog.isCatalogOpen();
    }

    @When("I search for {string}")
    public void iSearchFor(String productName) {
        world.catalog.searchFor(productName);
    }

    @Then("I view the product")
    public void iViewTheProduct() {
        world.product = world.catalog.viewProduct();
    }


    @When("I add the product to cart")
    public void iAddTheProductToCart() {
        world.cart = world.product.addToCart();
    }

    @Then("the cart page is open and cart badge updated to {int}")
    public void theCartPageIsOpenAndCartBadgeUpdatedTo(int count) {
        world.header().cartBadge().expectCount(count);
    }

    @When("I click on proceed to checkout")
    public void iClickOnProceedToCheckout() {
        world.checkout = world.cart.proceed();
    }

    @When("I click place order")
    public void iClickPlaceOrder() {
        world.checkout.placeOrder();
    }

    @Then("verify the order is created")
    public void verifyTheOrderIsCreated() {
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }


}
