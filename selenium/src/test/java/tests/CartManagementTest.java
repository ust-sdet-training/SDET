package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.SearchPage;
import utils.DriverFactory;

import java.util.List;

public class CartManagementTest {
    private WebDriver driver;
    private SearchPage searchPage;
    private CartPage cartPage;
    private String firstProductName;
    private String secondProductName;

    @BeforeClass
    public void setUpClass() {
        driver = DriverFactory.initDriver();
        searchPage = new SearchPage(driver);
        cartPage = new CartPage(driver);
    }

    @BeforeMethod
    public void setUpMethod() {
        driver.manage().deleteAllCookies();
        driver.get(DriverFactory.getBaseUrl());
    }

    @AfterClass
    public void tearDownClass() {
        DriverFactory.quitDriver();
    }

    @Test
    public void mainCartManagementFlow() {
        searchPage.openHomePage(DriverFactory.getBaseUrl());
        searchPage.searchProduct("shampoo");

        List<String> products = searchPage.collectFirstTwoProductNames();
        Assert.assertTrue(products.size() >= 2, "At least two products should be found");

        firstProductName = products.get(0);
        secondProductName = products.get(1);

        searchPage.addFirstTwoProducts();
        searchPage.openCart();

        Assert.assertTrue(cartPage.productExists(firstProductName), "First product should appear in cart");
        Assert.assertTrue(cartPage.productExists(secondProductName), "Second product should appear in cart");

        double totalBeforeRemove = cartPage.getTotalPrice();
        cartPage.removeProductByName(firstProductName);
        cartPage.confirmRemovePopup();

        Assert.assertFalse(cartPage.productExists(firstProductName), "Removed product should no longer be in cart");
        Assert.assertTrue(cartPage.productExists(secondProductName), "Remaining product should still be in cart");

        double totalAfterRemove = cartPage.getTotalPrice();
        Assert.assertTrue(totalAfterRemove <= totalBeforeRemove, "Total price should decrease or remain equal after removal");

        cartPage.refreshPage();
        Assert.assertTrue(cartPage.productExists(secondProductName), "Remaining product should persist after refresh");
    }

    @Test
    public void removeLastItemShowsEmptyCart() {
        searchPage.openHomePage(DriverFactory.getBaseUrl());
        searchPage.searchProduct("shampoo");

        List<String> products = searchPage.collectFirstTwoProductNames();
        Assert.assertFalse(products.isEmpty(), "At least one product should be found");

        firstProductName = products.get(0);
        searchPage.addProductByIndex(0);
        searchPage.openCart();

        Assert.assertTrue(cartPage.productExists(firstProductName), "Product should be in cart before removal");
        cartPage.removeProductByName(firstProductName);
        cartPage.confirmRemovePopup();

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should show an empty state");
        Assert.assertTrue(cartPage.isContinueShoppingButtonVisible(), "Continue Shopping button should be visible after last item removal");
    }

    @Test
    public void cartPersistsAfterRefresh() {
        searchPage.openHomePage(DriverFactory.getBaseUrl());
        searchPage.searchProduct("shampoo");

        List<String> products = searchPage.collectFirstTwoProductNames();
        Assert.assertTrue(products.size() >= 2, "At least two products should be found");

        firstProductName = products.get(0);
        secondProductName = products.get(1);

        searchPage.addFirstTwoProducts();
        searchPage.openCart();

        Assert.assertTrue(cartPage.productExists(firstProductName), "First product should be in cart");
        Assert.assertTrue(cartPage.productExists(secondProductName), "Second product should be in cart");

        double totalBeforeRefresh = cartPage.getTotalPrice();
        cartPage.refreshPage();

        Assert.assertTrue(cartPage.productExists(firstProductName), "First product should still be present after refresh");
        Assert.assertTrue(cartPage.productExists(secondProductName), "Second product should still be present after refresh");
        Assert.assertEquals(cartPage.getTotalPrice(), totalBeforeRefresh, 0.01, "Total should remain stable after refresh");
    }

    @Test
    public void duplicateAddBehavior() {
        searchPage.openHomePage(DriverFactory.getBaseUrl());
        searchPage.searchProduct("shampoo");

        List<String> products = searchPage.collectFirstTwoProductNames();
        Assert.assertFalse(products.isEmpty(), "At least one product should be found");

        firstProductName = products.get(0);
        searchPage.addProductByIndex(0);

        Assert.assertTrue(searchPage.isGoToCartVisibleForProduct(firstProductName), "Go to Cart should be visible after adding product");
    }
}
