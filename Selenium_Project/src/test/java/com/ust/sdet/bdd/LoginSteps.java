package com.ust.sdet.bdd;

import com.ust.sdet.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;


public class LoginSteps {

    public final World world;

    public LoginSteps(World world){
        this.world = world;
    }

    @Given("the login page is loaded")
    public void the_login_page_is_loaded() {
        world.login = new LoginPage(world.driver).open();
    }

    @When("the user enters {string} and {string}")
    public void the_user_enters_credentials(String username, String password) {
        world.login.email(username);
        world.login.password(password);
        assertTrue(world.login.isEmailVisible());
        assertTrue(world.login.isPasswordAccessible());
    }

    @When("clicks the sign in button")
    public void clicks_the_sign_in_button() {
        world.homePage = world.login.signIn();
        //assertTrue(world.login.isSignInEnabled());
    }

    @Then("the user should be redirected to home page")
    public void the_user_should_be_redirected_to_home_page() {

        assertTrue(
                world.homePage.isHeadingVisible(),
                "Home page should be displayed after successful login"
        );
        assertEquals("Welcome, Customer User",world.homePage.headingText());
    }

}
