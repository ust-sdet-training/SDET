package com.ust.sdet.bdd;

import com.ust.sdet.bdd.World;

import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.LoginPage;
import com.ust.sdet.support.Config;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {

    private final World world;

    public CheckoutSteps(World world){
        this.world = world;
    }

    @Given("the login page is open")
    public void loginPageOpen(){
        world.login = new LoginPage(world.driver).open();
    }

    @When("I login using {string} and {string}")
    public void login(String email, String password){
        world.login.login(email, password);
    }

    @Then("login should succeed")
    public void verifyLogin(){
        assertTrue(world.login.loggedIn());
    }

    @Given("the catalog is open")
    public void openCatalog(){
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I open the catalog")
    public void openCatalogAfterLogin(){
        world.driver.get(Config.catalogUrl());
        world.catalog = new CatalogPage(world.driver);
    }

    @When("I search for {string}")
    public void search(String product){
        world.catalog.searchFor(product);
    }

    @When("I add the first result to the cart")
    public void add(){
        world.product = world.catalog.openFirstProduct();
        world.cart = world.product.addToCart();
    }

    @Then("the cart badge shows {int}")
    public void badge(int expected){
        assertEquals(expected, world.header().cartBadge().count());
    }

    @When("I open the cart")
    public void openCart(){
        world.cart = world.header().openCart();
    }


    @Then("the cart has {int} line item")
    public void verifyCart(int expected){
        assertEquals(expected, world.cart.lineCount());
    }

    @Then("I capture the cart total")
    public void captureCartTotal(){
        String total = world.cart.total();
    }

    @When("I place the order")
    public void checkout(){
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void confirmed(){
        assertTrue(world.checkout.confirmationText().toLowerCase().contains("confirmed"));
    }
}