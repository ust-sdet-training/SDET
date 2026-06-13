package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static specs.SpecFactory.authSpec;

public class ProductTest {
    @Test
    void getProducts() {

        given()
                .spec(authSpec())
                .when()
                .get("/api/products")
                .then()
                .statusCode(200);
    }
}
