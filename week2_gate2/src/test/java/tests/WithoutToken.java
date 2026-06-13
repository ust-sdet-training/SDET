package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class WithoutToken {

    private static final String BASE_URL = "http://localhost:4000";
    @Test
    void withoutToken(){
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/secure/orders/5001")
                .then()
                .log().all()
                .statusCode(401);
    }
}
