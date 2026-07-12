package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.support.World;
import com.shopkart.support.paisaConversion;
import com.shopkart.ui.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_lol.WEN;
import org.openqa.selenium.devtools.latest.emulation.model.WorkAreaInsets;

import static com.shopkart.support.paisaConversion.toPaisa;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndSteps {
    private final World world;

    public EndToEndSteps(World world) {
        this.world = world;
    }

    @Given("The User Logged in with Valid Credentials")
    public void makeAValidLogin(){

        this.world.login = new LoginPage();
        this.world.catalog = this.world.login.goToLoginPage().makeALogin("alice@shopkart.test","alice@shopkart.test");
        this.world.apiflow = new ApiEndToEndTest();
    }

    @When("I go to Catalog page and searchFor product {string}")
    public void goToCatalogPageAndSearchTheProduct(String product){
        this.world.catalog.searchTheProduct(product);
        this.world.apiflow.searchProduct(product);
    }

    @Then("I got the matched product for {string}")
    public void isGotAMatchedProduct(String product){
       this.world.catalog.VerifyTheProduct(product);
    }
    @And("Selected the Product")
    public void selectTheProduct(){
        this.world.product = this.world.catalog.goToProductPage();
    }



    @When("Added the Product {string} to the Cart of quantity {int}")
    public void addToTheCart(String product,int qty){
        this.world.cart = this.world.product.addToCartWithLogin();
        this.world.apiflow.addProductToCart(product,qty);
    }

    @And("Place the Order at Address {string}")
    public void placeTheOrder(String address){
        this.world.checkout = this.world.cart.makeACheckout().placeTheOrder();
        this.world.response = this.world.apiflow.placeOrder(address);
    }

   @Then("The get api\\/orders returns PLACED and validated the totalpaisa")
    public void isOrderPlaced(){

        this.world.apiflow.validateOrder(this.world.response);
       String total = String.valueOf(paisaConversion.toPaisa(this.world.checkout.getTotal()));
       assertAll(
               ()->assertEquals(total,this.world.response.then().extract().path("totalPaise").toString())
       );
   }

   @Then("The Database is Validated")
    public void databaseValidation() throws Exception {
        this.world.apiflow.validateDatabase(this.world.response);
   }

}
