package week3.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Login {

    private final World world;

    public Login(World world) {
        this.world = world;
    }

    @Given("the login page is open")
    public void theLoginPageIsOpen() {

        world.login =
                new week3.pages.LoginPage(world.driver)
                        .open();
    }

    @When("I login with {string} and {string}")
    public void iLoginWithAnd(String email, String password) {

        world.homePage =
                world.login
                        .email(email)
                        .password(password)
                        .signIn();
    }

    @Then("the heading is displayed")
    public void theHeadingIsDisplayed() {

        assertTrue(
                world.homePage.isHeadingVisible()
        );
    }

    @When("I navigate to the catalog page")
    public void iNavigateToTheCatalogPage() {

        world.catalog =
                world.homePage.gotoCatalog();
    }

    @Then("the catalog page is displayed")
    public void theCatalogPageIsDisplayed() {

        assertTrue(
                world.catalog.cards().size() > 0
        );
    }
}