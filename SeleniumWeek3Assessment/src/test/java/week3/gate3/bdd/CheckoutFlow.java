package week3.gate3.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import week3.gate3.pages.CatalogPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutFlow {
    private final World world;

    public CheckoutFlow(World world){
        this.world = world;
    }

    @Given("the catalog is open")
    public void openCatalog(){
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I search for {string}")
    public void searchForProduct(String query){
        world.catalog.search(query);
    }

    @When("I add the first item to the cart")
    public void addFirstItem(){
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void cartBadgeCount(int n){
        world.header().cartBadge().expectCount(n);
    }

    @Then("I open the cart")
    public void openCart(){
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line items")
    @Then("the cart has {int} line item")
    public void checkCartElements(int count){
        assertEquals(count, world.cart.cartElementCount());
    }

    @When("I place the order")
    public void placeOrder(){
        world.checkout = world.cart.checkout().placeOrder();
    }

    @Then("the order is confirmed")
    public void confirmOrder(){
        assertTrue(world.checkout.confirmOrder().toLowerCase().contains("confirmed"));
    }

    @Then("the first product title is {string}")
    public void checkTitle(String title){
        assertEquals(title, world.catalog.firstCardTitle());
    }


}
