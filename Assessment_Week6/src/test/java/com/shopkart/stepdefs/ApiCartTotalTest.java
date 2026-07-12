package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.db.DBHelper;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiCartTotalTest {

    ProductClient client = new ProductClient();

    @Step("Add Product To Cart")
    public void addProductToCart(String product, int qty) {

        client.addToCart(product, qty)
                .then()
                .statusCode(200);
    }

    @Step("Get Cart Details")
    public Response getCart() {

        return client.getCartItems();
    }

    @Step("Validate Cart Total")
    public void validateCartTotal(Response response) {

        int qty = response.path("items[0].qty");
        int unitPrice = response.path("items[0].unitPricePaise");
        int totalPaise = response.path("totalPaise");

        assertEquals(
                qty * unitPrice,
                totalPaise
        );
    }

    @Step("Validate Cart Total From Database")
    public void validateCartTotalFromDB(Response response) {

        int cartId = response.path("cartId");

        int apiTotal = response.path("totalPaise");

        int dbTotal =
                DBHelper.getCartTotal(cartId);

        assertEquals(apiTotal, dbTotal);
    }

    @Step("Complete Cart Total Validation")
    public void validateCartTotalJourney() {

        addProductToCart(Secrets.get("product"), Integer.parseInt(Secrets.get("Quantity")));

        Response response = getCart();

        validateCartTotal(response);

        validateCartTotalFromDB(response);
    }
}