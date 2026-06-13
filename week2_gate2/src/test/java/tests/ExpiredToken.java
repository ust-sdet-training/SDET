package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class ExpiredToken {

    @Test
    void expiredOrInvalidToken() {

        given()
                .baseUri("http://localhost:4000")
                .header("Authorization", "Bearer expired-token")
                .when()
                .get("/api/secure/orders/5001")
                .then()
                .log().all()
                .statusCode(401)
                .header("WWW-Authenticate", containsString("Bearer"));
    }
}
