package tests;

import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;

public class NonExistingOrder {
    @Test
    void readNonExistingOrder() {

        given()
                .spec(SpecFactory.authSpec())
                .when()
                .get("/api/secure/orders/99999")
                .then()
                .log().all()
                .statusCode(404);
    }
}
