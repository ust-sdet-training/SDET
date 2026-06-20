package com.example.Selenium.bdd;

import com.example.Selenium.pages.CatalogPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {
    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("the catalog is open")
    public void theCatalogIsOpen(){
        world.catalog=new CatalogPage(world.driver).open();
    }

    @When("I open a invalid Product {string}")
    @When("I opened a Product {string}")
    public void isSearchForProduct(String query){
        world.catalog.searchFor(query);
    }

    @Then("It Shows No Products")
    public void noProducts(){
        assertTrue(world.catalog.emptySearchMessage().toLowerCase().contains("no"));
    }

    @And("I add a first result in CartBadge")
    public void iaddTheFirstResultToTheCart(){
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }

    @Then("the CartBadge shows {int}")
    public void theCartShows(int count){
        world.header().cartBadge().expectedCount(count);
    }

    @When("I open a Cart")
    public void isOpenTheCart(){
        world.cart = world.header().opencart();
    }

    @Then("It shows the cart lines {int}")
    @Then("It shows the cart line {int}")
    public void isCartHasLineItems(int count){
        assertEquals(count, world.cart.lineCount());
    }

    @When("I place an Order")
    public void iPlaceTheOrder(){
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("It shows the message Confirmed")
    public void confirmationMessage(){
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }

    @Then("it shows cart line as {int}")
    public void it_shows_cart_line_as(Integer count){
        assertEquals(count, world.cart.lineCount());
    }

}
