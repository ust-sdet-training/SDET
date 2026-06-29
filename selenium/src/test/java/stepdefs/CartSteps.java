package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CartPage;
import pages.SearchPage;
import utils.DriverFactory;

import java.util.List;

public class CartSteps {
    private SearchPage searchPage;
    private CartPage cartPage;
    private String firstProductName;
    private String secondProductName;
    private double totalBeforeRemove;
    private double totalAfterRemove;

    @Given("the user opens the Purplle homepage")
    public void openHomepage() {
        // initialize pages after driver is ready
        DriverFactory.initDriver();
        searchPage = new SearchPage(DriverFactory.getDriver());
        cartPage = new CartPage(DriverFactory.getDriver());
        searchPage.openHomePage(DriverFactory.getBaseUrl());
    }

    @When("the user searches for {string}")
    public void searchForProduct(String query) {
        searchPage.searchProduct(query);
    }

    @And("collects the first two product names")
    public void collectFirstTwoProductNames() {
        List<String> products = searchPage.collectFirstTwoProductNames();
        if (!products.isEmpty()) {
            firstProductName = products.get(0);
        }
        if (products.size() > 1) {
            secondProductName = products.get(1);
        }
    }

    @And("adds the first two products to the cart")
    public void addFirstTwoProducts() {
        searchPage.addFirstTwoProducts();
    }

    @And("opens the cart")
    public void openCart() {
        searchPage.openCart();
    }

    @Then("both captured products should be present in the cart")
    public void verifyBothProductsPresent() {
        Assert.assertTrue(cartPage.productExists(firstProductName), "First product should be present in cart");
        Assert.assertTrue(cartPage.productExists(secondProductName), "Second product should be present in cart");
    }

    @And("captures the cart total")
    public void captureCartTotal() {
        totalBeforeRemove = cartPage.getTotalPrice();
    }

    @And("removes the first product")
    public void removeFirstProduct() {
        cartPage.removeProductByName(firstProductName);
        cartPage.confirmRemovePopup();
    }

    @Then("the removed product should not appear in the cart")
    public void verifyRemovedProductAbsent() {
        Assert.assertFalse(cartPage.productExists(firstProductName));
    }

    @Then("the remaining product should still be present")
    public void verifyRemainingProductPresent() {
        Assert.assertTrue(cartPage.productExists(secondProductName));
    }

    @And("captures the updated cart total")
    public void captureUpdatedCartTotal() {
        totalAfterRemove = cartPage.getTotalPrice();
        Assert.assertTrue(totalAfterRemove <= totalBeforeRemove, "Updated total should be less or equal after removal");
    }
}
