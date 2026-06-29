package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import spec.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {
    @Test
    void validateHeaders() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts");
        response.then()
                .spec(SpecFactory.success200());
        assertTrue(
                response.getHeader("Content-Type")
                        .contains("application/json"));
    }

    @Test
    void validateResponseTime() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts/1");
        assertTrue(response.time() < 5000);
    }
}
