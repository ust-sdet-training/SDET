package com.ust.capstone.stepdefs;

import com.ust.capstone.api.ApiSpec;
import com.ust.capstone.api.AuthClient;
import com.ust.capstone.api.model.LoginResponse;
import com.ust.capstone.data.TestUsers;
import com.ust.capstone.data.secret.Secrets;
import com.ust.capstone.bdd.WorldContext;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class LoginSteps {

    private final WorldContext context;

    public LoginSteps(WorldContext context) {
        this.context = context;
    }

    @Given("{string} has account")
    public void userHasAccount(String user) {

    }

    @When("{string} logs in")
    public void logsIn(String user) {

        Credentials credentials = credentialsFor(user);

        // API Login
        Response response = AuthClient.login(
                credentials.email(),
                credentials.password()
        );
        context.setResponseStatus(response.getStatusCode());

        LoginResponse loginResponse = response.then()
                .spec(ApiSpec.okResponse())
                .extract()
                .as(LoginResponse.class);

        context.setToken(loginResponse.token());
        context.setEmpId(loginResponse.empId());
        context.setRole(loginResponse.role());
        context.setDisplayName(loginResponse.displayName());
    }

    @Then("he should receive {int} response")
    public void receiveResponse(int expectedStatus) {
        assertEquals(expectedStatus, context.getResponseStatus());
    }

    private Credentials credentialsFor(String user) {
        return switch (user.toLowerCase()) {
            case "bob" -> new Credentials(
                    TestUsers.BOB_EMAIL,
                    Secrets.get("TRIPSTACK_BOB_PASSWORD")
            );

            case "dave" -> new Credentials(
                    TestUsers.DAVE_EMAIL,
                    Secrets.get("TRIPSTACK_BOB_PASSWORD")
            );
            default -> throw new IllegalArgumentException(
                    "Unknown user: " + user
            );

        };
    }

    private record Credentials(String email, String password) {
    }

    @And("the token is tampered")
    public void theTokenIsTampered() {

    }
}