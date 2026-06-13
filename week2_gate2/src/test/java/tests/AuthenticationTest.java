package tests;


import config.ConfigReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    @Test
    void tokenShouldBeGenerated() {
        String token = TokenManager.getToken();
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void checkTokenEndpoint() {

        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .auth()
                        .preemptive()
                        .basic(
                                ConfigReader.CLIENT_ID,
                                ConfigReader.CLIENT_SECRET
                        )
                        .formParam(
                                "grant_type",
                                "client_credentials"
                        )
                        .when()
                        .post(
                                ConfigReader.BASE_URL +
                                        "/api/oauth/token"
                        );

        System.out.println("STATUS = " + response.statusCode());
        System.out.println(response.asPrettyString());
    }
}
