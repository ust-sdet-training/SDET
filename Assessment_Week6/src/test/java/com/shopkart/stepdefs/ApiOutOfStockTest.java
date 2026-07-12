package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.data.secrets.Secrets;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ApiOutOfStockTest {

    ProductClient client = new ProductClient();

    @Step
    public Response addMoreThanAvailableStock(String product) {

        return client.addOutOfStockItem(product);
    }

    @Step
    public void validateConflict(Response response) {

        response.then()
                .statusCode(409);
    }

    @Step
    public void startOutOfStockTest(){
        Response response = addMoreThanAvailableStock(Secrets.get("product"));
        validateConflict(response);
    }
}
