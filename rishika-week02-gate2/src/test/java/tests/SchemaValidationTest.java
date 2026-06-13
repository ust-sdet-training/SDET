package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest extends BaseTest {

    @Test
    void validateOrderSchema() {

        String token = TokenManager.getToken();

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("items", new int[]{101, 107}, "currency", "INR"))
                .when()
                .post("/api/secure/orders")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/order-schema.json"));
    }
}