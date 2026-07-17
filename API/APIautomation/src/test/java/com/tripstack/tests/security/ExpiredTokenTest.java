package com.tripstack.tests.security;

import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Tag("api")
@Tag("security")
public class ExpiredTokenTest {

    // A deliberately invalid/tampered JWT - not a real credential.
    private static final String EXPIRED_OR_TAMPERED_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleHBpcmVkIn0.invalidsignature";

    @Test
    void expiredOrTamperedTokenIsRejected() {
        Response response = given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + EXPIRED_OR_TAMPERED_TOKEN)
                .when()
                .get("/auth/me");

        response.then().statusCode(401);
    }
}