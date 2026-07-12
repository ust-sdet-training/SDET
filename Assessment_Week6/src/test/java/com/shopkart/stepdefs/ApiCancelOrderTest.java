package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ApiCancelOrderTest {

    ProductClient client = new ProductClient();

    private int orderId;

    @Step
    public void createPlacedOrder(String product,int qty,String address) {

        client.addToCart(product, qty);

        Response response =
                client.placeOrderAt(address);

        orderId = response.path("id");
    }
    @Step
    public Response cancelOrder() {
        return client.cancelOrder(orderId);
    }

    @Step
    public Response cancelAgain() {
        return client.cancelOrder(orderId);
    }

    @Step
    public void checkTheCancelTest(){

        createPlacedOrder(Secrets.get("product"), Integer.parseInt(Secrets.get("quantity")),Secrets.get("Address"));

        cancelOrder();

        Response response = cancelAgain();

        response.then().statusCode(409);
    }
}
