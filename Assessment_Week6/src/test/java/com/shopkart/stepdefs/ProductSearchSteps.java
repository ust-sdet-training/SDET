package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.support.World;
import com.shopkart.ui.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSearchSteps {
    private final World world;

    public ProductSearchSteps(World world) {
        this.world = world;
    }
    @Given("The User Go to Catalog Page")
    public void makeloginwithValidCredentials() {

        world.catalog = new CatalogPage().goToCatalog();
        world.apiproductflow = new ApiProductSearchTest();
    }

    @When("Searched for product {string} and Select it")
    public void searchForAProduct(String product) {

        world.catalog.searchTheProduct(product);
        world.apiproductflow.searchProduct(product);
    }

    @Then("Go to the matched product for {string} and validated Response")
    public void verifyTheProduct(String product) {

        assertTrue(world.catalog.goToCatalog().VerifyTheProduct(product));

        world.apiproductflow.validateTheProduct(product);
    }

}
