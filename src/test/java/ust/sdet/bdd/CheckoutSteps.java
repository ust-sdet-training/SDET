package ust.sdet.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ust.sdet.pages.CatalogPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {


    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("The catalog page is open")
    public void openCatalogPage(){
        world.catalog = new CatalogPage(world.driver).open();
    }
    @When("The current catalog page is open")
    public void opencurrentCatalogPage(){
        world.catalog = new CatalogPage(world.driver);
    }
    @When("I search for {string}")
    public void searchProduct(String product){
        world.catalog.searchForProduct(product);
    }
    @When("I add the first result to the cart")
    public void addToCartProduct(){
        world.products = world.catalog.openFirstProduct();
        world.cart = world.products.addToCart();
    }
    @Then("the cart badge shows {int}")
    public void checkCartBadgeCount(int count){
        world.header().cartBadge().expectedCount(count);
    }

    @When("I open the cart")
    public void openCartPage(){
        world.cart=world.header().opencart();
    }

    @Then("the cart has {int} line items")
    @Then("the cart has {int} line item")
    public void checkCartCount(int count){

        assertEquals(count, world.cart.lineCount());

    }
    @When("I place the order")
    public void placeOrder(){
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("The order is confirmed")
    public void checkOrderConfirmed(){
        assertTrue(
                world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
    @Then("show insufficient stock error")
    public void showInsufficientStockError(){
        assertTrue(
                world.products
                        .insufficientStockText()
                        .toLowerCase()
                        .contains("Unable to add")
        );
    }
}

