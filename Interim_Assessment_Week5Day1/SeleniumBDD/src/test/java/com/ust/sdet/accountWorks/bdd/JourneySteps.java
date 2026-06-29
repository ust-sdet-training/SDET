package com.ust.sdet.accountWorks.bdd;

import com.ust.sdet.accountWorks.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JourneySteps {
    private final WebPage webPage;

    public JourneySteps(WebPage webPage){
        this.webPage = webPage;
    }

    @Given("the amazon app is open")
    public void theAppIsOpen(){
        webPage.home = new HomePage(webPage.driver).openURL();
    }

    @Given("I am on home page")
    public void amI_OnHomePage(){
        webPage.home.isThisHome().equalsIgnoreCase("amazon.in");
    }

    @When("I search for {string}")
    public void iSearchFor(String query){
        webPage.home.searchFor(query);
    }

    @When("I click Search")
    public void iClickSearch(){
        webPage.productDisplay = webPage.home.clickSearch();
    }

    @Then("I see search results page loads")
    public void iSeeSearchResultsThePageLoads(){
        webPage.productDisplay.areProductsAvailable();
    }

    @When("I capture all displayed product names")
    public void iCaptureAllDisplayedProductNames(){
        webPage.productDisplay.productTitles();
    }

    @When("I Select any product dynamically from the first page")
    public void iSelectAnyProductDynamicallyFromFirstPage(){
        webPage.productDisplay.scrollToTheProduct();
    }

    @Then("I validate product title is displayed")
    public void iValidateProductTitleIsDisplayed(){
        String productName = webPage.productDisplay.isProductTitleSame();
        webPage.productDisplay.productTitleIsVisible();
    }
    @Then("I validate product price is available")
    public void iValidateProductPriceIsDisplayed(){
        webPage.productDisplay.productPriceIsVisible();
    }

    @Then("I validate product rating (if available)")
    public void iValidateProductRating(){
        webPage.productDisplay.productRatingIsVisible();
    }

    @When("I open the product")
    public void iOpenTheProduct(){
        webPage.product = webPage.productDisplay.openFirstProduct();
    }

    @Then("I see product page title contains the product name {string}")
    public void iSeeProductPageTitleContainsTheProductName(String query){
        webPage.product.productTitleContainsProductName(query);
    }
}
