package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class InvalidClientSecret {
    @Test
    void invalidCredentialsShouldReturn401() {

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .accept("application/json")
                .auth()
                .preemptive()
                .basic("retail-ops-client", "wrong-secret")
                .formParam("grant_type", "client_credentials")

                .when()
                .post("http://localhost:4000/api/oauth/token")

                .then()
                .log().all()
                .statusCode(401);
    }
}
