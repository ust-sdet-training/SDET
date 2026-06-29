package com.ust.sdet.bdd;

import com.ust.sdet.pages.SearchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CheckoutSteps {


    private final World world;

    public CheckoutSteps(World world) {
        this.world = world;
    }

    @Given("The searchpage page is open")
    public void openCatalogPage(){
        world.searchPage = new SearchPage(world.driver).open();
    }


    @When("I search for bus between {string} and {string} on {string}")
    public void searchForBus(String sourceCity,String destinationCity, String date){

        world.searchPage.searchBus(sourceCity,destinationCity,date);

    }


}