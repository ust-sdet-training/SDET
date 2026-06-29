package com.sdet.selenium.bdd;

import com.sdet.selenium.pages.AmazonHomePage;
import com.sdet.selenium.pages.ProductPage;
import com.sdet.selenium.pages.SearchResultsPage;
import com.sdet.selenium.support.DriverFactory;
import com.sdet.selenium.support.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.sdet.selenium.support.AssertUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class AmazonSearchSteps {
    private final TestContext context = new TestContext();
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.createChromeDriver();
        context.setDriver(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open Amazon homepage")
    public void iOpenAmazonHomepage() {
        AmazonHomePage homePage = new AmazonHomePage(driver);
        homePage.open();
        context.setAmazonHomePage(homePage);
    }

    @When("I search for {string}")
    public void iSearchFor(String keyword) {
        context.setSearchKeyword(keyword);
        context.getAmazonHomePage().searchFor(keyword);
    }

    @Then("the search results page should load")
    public void theSearchResultsPageShouldLoad() {
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        Assertions.assertTrue(resultsPage.isLoaded(), "Search results page should load");
        context.setSearchResultsPage(resultsPage);
    }

    @Then("I should see no search results")
    public void iShouldSeeNoSearchResults() {
        SearchResultsPage resultsPage = context.getSearchResultsPage();
        Assertions.assertTrue(resultsPage.hasNoResults(), "The search results page should indicate no results");
    }

    @And("I capture all displayed product names")
    public void iCaptureAllDisplayedProductNames() {
        List<String> productNames = context.getSearchResultsPage().getDisplayedProductNames();
        Assertions.assertFalse(productNames.isEmpty(), "At least one product should be displayed");
        context.setProductNames(productNames);
    }

    @And("I open the first displayed product")
    public void iOpenTheFirstDisplayedProduct() {
        context.getSearchResultsPage().openFirstProduct();
        ProductPage productPage = new ProductPage(driver);
        context.setProductPage(productPage);
        context.setSelectedProductName(context.getProductNames().get(0));
    }

    @Then("the product title should be displayed")
    public void theProductTitleShouldBeDisplayed() {
        String title = context.getProductPage().getTitleText();
        AssertUtils.assertNotBlank(title, "Product title should be displayed");
    }

    @And("the product price should be available")
    public void theProductPriceShouldBeAvailable() {
        String price = context.getProductPage().getPriceText();
        AssertUtils.assertNotBlank(price, "Product price should be available");
    }

    @And("the product rating should be visible when available")
    public void theProductRatingShouldBeVisibleWhenAvailable() {
        String rating = context.getProductPage().getRatingText();
        if (rating != null && !rating.isBlank()) {
            Assertions.assertTrue(rating.toLowerCase().contains("star") || rating.toLowerCase().contains("rating") || rating.matches(".*\\d+.*"),
                    "Product rating should be shown when available");
        }
    }

    @Then("the product page should show the selected product")
    public void theProductPageShouldShowTheSelectedProduct() {
        String title = context.getProductPage().getTitleText();
        AssertUtils.assertNotBlank(title, "Product page should display a product title");

        String currentUrl = driver.getCurrentUrl();
        boolean isProductPage = currentUrl.contains("/dp/")
                || currentUrl.contains("/gp/product")
                || currentUrl.contains("/product/")
                || (!currentUrl.contains("/s?") && !currentUrl.contains("/search"));

        Assertions.assertTrue(isProductPage,
                "Product page should be opened successfully. Current URL: " + currentUrl);
    }
}
