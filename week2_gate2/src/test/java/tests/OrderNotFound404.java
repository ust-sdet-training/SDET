package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static specs.SpecFactory.authSpec;

public class OrderNotFound404 {
    @Test
    void orderNotFound() {

        given()
                .spec(authSpec())
                .when()
                .get("/api/orders/999999")
                .then()
                .statusCode(404);
    }
}
