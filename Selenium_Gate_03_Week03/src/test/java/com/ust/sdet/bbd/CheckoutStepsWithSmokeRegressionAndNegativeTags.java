package com.ust.sdet.bbd;

import com.ust.sdet.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutStepsWithSmokeRegressionAndNegativeTags {

    private final World world;

    public CheckoutStepsWithSmokeRegressionAndNegativeTags(World world) {
        this.world = world;
    }

    private void addFirstProductToCart() {
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }


    @Given("I am browsing the product catalog")
    public void iAmBrowsingTheProductCatalog() {
        world.catalog = new CatalogPage(world.driver).open();
    }


    @When("I look for {string}")
    public void iSearchForSpecificProduct(String product) {
        world.catalog = new CatalogPage(world.driver).open();
        world.catalog.searchFor(product);
    }

    @When("I choose the first product from the results and add it to my cart")
    public void chooseFirstProduct() {
        addFirstProductToCart();
    }

    @When("I add the first available product to the cart")
    public void addFirstProduct() {
        addFirstProductToCart();
    }

    @When("I add the first available result to the cart")
    public void addFirstResult() {
        addFirstProductToCart();
    }

    @When("I navigate to my cart")
    public void iNavigateToCart() {
        world.cart = world.header().openCart();
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        String total = world.cart.total();
        world.scenario.attach(
                total.getBytes(),
                "text/plain",
                "Cart Total Before Checkout"
        );

        world.checkout = world.cart.proceed();

    }


    @Then("I should see the cart badge updated to {int}")
    public void verifyCartBadge(int count) {
        world.header().cartBadge().expectedCount(count);
    }

    @Then("I should see {int} item listed in the cart")
    public void verifyCartItemsListed(int count) {
        assertEquals(count, world.cart.lineCount(),
                "Cart item count mismatch");
    }

    @Then("I should see {int} item in the cart")
    public void verifyCartItems(int count) {
        assertEquals(count, world.cart.lineCount(),
                "Cart item count mismatch");
    }

    @Then("I should see no items in the cart")
    public void verifyEmptyCart() {
        assertEquals(0, world.cart.lineCount(),
                "Cart is expected to be empty");
    }

    @Then("my order should be placed successfully")
    public void verifyOrderSuccess() {
        world.checkout.placeOrder();
        assertTrue(world.checkout.confirmationText()
                        .toLowerCase().contains("confirmed"),
                "Order confirmation text not found");
    }
}