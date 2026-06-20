package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CatalogPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndToEndFlow {

        private final World world;

        public EndToEndFlow(World world){
            this.world=world;
        }

        @Given("the catalog is open")
        public void openCalatalogPage(){
            world.catalog = new CatalogPage(world.driver).open();

            assertTrue(world.driver.getCurrentUrl().contains("catalog"),
                    "Catalog page not loaded");

        }

        @When("I search for {string}")
        public void iSearchFor(String query){
            world.catalog.searchFor(query,"");
        }

        @When("I add the first result to the cart")
        public void iAddFirstResultToCart(){
            world.product = world.catalog.openFirstProduct();
            world.product.addToCart();

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
            world.checkout = world.cart.cartButton();
            world.checkout.placeorder();

            assertTrue(world.driver.getCurrentUrl().contains("checkout"),
                    "Not navigated to checkout");

        }

        @Then("the order is confirmed")
        public void theorderIsConfirmed(){
            world.checkout.confirmationText();
        }


}


