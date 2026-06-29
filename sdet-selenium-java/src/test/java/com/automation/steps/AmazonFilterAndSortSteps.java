package com.automation.steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmazonFilterAndSortSteps {

    private final World world;

    public AmazonFilterAndSortSteps(World world){
        this.world = world;
    }

    @Given("the user is on amazon website")
    public void theUserIsOnAmazonWebsite() {
        world.amazonHomePage=  world.amazonHomePage().openAmazon();
    }

    @When("the user enters {string} in search box")
    public void theUserEntersInSearchBox(String product) {
        world.amazonHomePage=   world.amazonHomePage.searchProduct(product);
    }

    @Then("the user clicks search submit button")
    public void theUserClicksSearchSubmitButton() {
        world.productListingPage= world.amazonHomePage.clickSearchSubmitButton();
    }

    @And("the user applies brand filter {string}")
    public void theUserAppliesBrandFilter(String brand) {
        world.productListingPage=world.productListingPage.selectBrand(brand);
    }

    @And("the user sorts products by {string}")
    public void theUserSortsProductsBy(String by) {
        world.productListingPage= world.productListingPage.sortBy(by);
    }

    @Then("verify the products are sorted by method")
    public void verifyTheProductsAreSortedByMethod(){
        assertTrue(world.productListingPage.areProductsSortedByLowToHigh());
    }

    @And("the user clicks on first product")
    public void theUserClicksOnFirstProduct() {
        world.productListingPage=  world.productListingPage.clickProduct();
    }

    @Then("verify the product belongs to the brand {string}")
    public void verifyTheProductBelongsToTheBrand(String brand) {
            assertTrue(world.productListingPage.isProductBelongsTo(brand));
    }
}
