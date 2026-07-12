package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.db.DBSupport;
import com.shopkart.data.db.OrderRepository;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ApiEndToEndTest {

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

    @Step("Validate DB Record")
    public void validateDatabase(Response response)
            throws Exception {

        int orderId =
                response.path("id");

        Connection con =
                DBSupport.getConnection();

        OrderRepository repo =
                new OrderRepository(con);

        assertEquals(
                "PLACED",
                repo.getOrderStatus(orderId)
        );

        assertEquals(
                (Integer) response.path("totalPaise"),
                repo.getTotalPaise(orderId)
        );
    }

    @Step("Complete API Journey")
    public void makeAFullApiJourney() throws Exception {

        searchProduct(Secrets.get("product"));

        addProductToCart(Secrets.get("product"), Integer.parseInt(Secrets.get("quantity")));

        Response response =
                placeOrder(Secrets.get("Address"));

        validateOrder(response);

        validateDatabase(response);
    }
}