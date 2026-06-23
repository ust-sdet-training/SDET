package tests;

import base.BaseTest;

import org.junit.jupiter.api.Test;
import specs.ResponseSpec;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeleteOrderTest extends BaseTest {

    @Test
    public void deleteOrderTest() {

        given()
                .spec(requestSpec)
                .pathParam("orderId", 101)

                .when()
                .delete("/store/order/{orderId}")

                .then()
                .spec(ResponseSpec.success200())
                .body("code", equalTo(200))
                .body("message", equalTo("101"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/delete-order.schema.json"));
    }
}