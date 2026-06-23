package tests;

import base.BaseTest;

import org.junit.jupiter.api.Test;
import specs.ResponseSpec;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest extends BaseTest {

    @Test
    public void getOrderTest() {

        given()
                .spec(requestSpec)
                .pathParam("orderId", 101)

                .when()
                .get("/store/order/{orderId}")

                .then()
                .spec(ResponseSpec.notfound404())
                .body("id", equalTo(101))
                .body("petId", equalTo(1001))
                .body("status", equalTo("placed"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/order.schema.json"));
    }
}