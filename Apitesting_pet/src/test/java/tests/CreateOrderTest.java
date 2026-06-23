package tests;

import base.BaseTest;

import org.junit.jupiter.api.Test;
import specs.ResponseSpec;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {

    @Test
    public void createOrderTest() {

        String payload = """
                {
                  "id": 101,
                  "petId": 1001,
                  "quantity": 2,
                  "shipDate": "2026-06-23T10:00:00.000Z",
                  "status": "placed",
                  "complete": true
                }
                """;

        given()
                .spec(requestSpec)
                .body(payload)

                .when()
                .post("/store/order")

                .then()
                .spec(ResponseSpec.success200())
                .body("id", equalTo(101))
                .body("petId", equalTo(1001))
                .body("quantity", equalTo(2))
                .body("status", equalTo("placed"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/create-order.schema.json"));
    }
}