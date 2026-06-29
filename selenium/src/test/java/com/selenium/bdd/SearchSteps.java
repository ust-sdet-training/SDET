package com.selenium.bdd;

import com.selenium.page.HomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {
    private final World world;
    public SearchSteps(World world){
        this.world=world;
    }

    @Given("the home is open")
    public void openCalatalogPage(){
        world.homePage= new HomePage(world.driver).open();
    }

    @When("I set the source location {string}")
    public void setSource(String source){
        world.homePage.setSoruce(source);
    }

    @When("I set the desitination location {string}")
    public void setDestination(String source){
        world.homePage.setSoruce(source);
    }

    @When("I set the search")
    public void search(){
        world.homePage.search();
    }

    
}
