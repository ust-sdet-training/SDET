package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class InvalidToken {

    private static final String BASE_URL = "http://localhost:4000";
    @Test
    void invalidToken_headerCheck() {

        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer invalid-token")
                .when()
                .get("/api/orders/5001")
                .then()
                .statusCode(401)
                .header(
                        "WWW-Authenticate",
                        containsString("Bearer")
                );
    }
}
