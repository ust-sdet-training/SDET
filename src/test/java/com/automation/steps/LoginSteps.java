package com.automation.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private final World world;

    public LoginSteps(World world){
        this.world = world;
    }

    @When("I click sign in button in home page")
    public void iClickSignInButtonInHomePage() {
        world.login = world.home.clickSignIn();
    }

    @And("I enter {string} and {string}")
    public void iEnterEmailAndPassword(String email,String password) {
        world.login.enterUserNameAndPassword(email,password);
    }

    @When("I click sign in button")
    public void iClickSignInButton() {
        world.home = world.login.clickSignIn();
    }

    @Then("I can see sign out button in home page")
    public void iCanSeeSignOutButtonInHomePage() {
        assertTrue(world.home.isSignOutButtonPresent());
    }

    @Then("I wait for sign in to enable")
    public void iWaitForSignInToEnable() {
        world.login.waitForSignInEnable();
    }
}
