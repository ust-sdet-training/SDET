package com.ust.capstone.stepdefs;

import com.ust.capstone.api.BusClient;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static com.ust.capstone.api.ApiSpec.okResponse;
import static org.hamcrest.Matchers.equalTo;

public class BusSearchSteps {
    private final BusClient busClient = new BusClient();
    private Response response;

    @When("{string} searches from {string} to {string} on {string}")
    public void searchesFromToOn(String name, String from, String to, String date) {
        response = busClient.search(from, to, date);
    }

    @Then("the API should return {string} {string} {string}")
    public void apiShouldReturn(String from, String to, String date) {
        response
            .then()
                .spec(okResponse())
                .body("from", equalTo(from))
                .body("to", equalTo(to))
                .body("count", equalTo(response.jsonPath().getList("buses").size()));
    }
}