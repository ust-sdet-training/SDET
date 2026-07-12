package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiOrderAccess {

    ProductClient client = new ProductClient();

    @Step("Search Product")
    public String searchProduct(String product) {
        return client.getProducts(product)
                .then()
                .extract()
                .path("[0].sku");
    }

    @Step("Add Product To Cart")
    public void addProductToCart(String product, int qty) {
        client.addToCart(product, qty)
                .then()
                .statusCode(200);
    }

    @Step("Place Order")
    public Response placeOrder(String address) {
        return client.placeOrderAt(address);
    }

    @Step("Validate Order")
    public void validateOrder(Response response) {
        assertAll(
                () -> assertEquals(
                        "placed",
                        response.then().extract().path("status").toString().toLowerCase()
                )
        );
    }

    @Step("Complete API Journey")
    public void makeAFullApiJourney() {

        searchProduct(Secrets.get("product"));

        addProductToCart(Secrets.get("product"), Integer.parseInt(Secrets.get("quantity")));

        Response response =
                placeOrder(Secrets.get("Address"));

        validateOrder(response);
    }

    @Step("Bob trying to access Alice")
    public Response bobLogin(){
        return client.unathorizedAcess();
    }

    @Step("Unathorized User Access")
    public void unauthorizedAccess(){
        bobLogin();
    }

    @Step("Unathorized access detected")
    public void unauthorizedAccessDetected(){
        bobLogin().then().statusCode(403);
    }




}
