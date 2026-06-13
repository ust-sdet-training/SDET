
package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationTest extends BaseTest {

    @Test
    void generateAccessToken() {

        String token = TokenManager.getToken();

    }
    @Test
    void readSecureOrder() {

        String token = TokenManager.getToken();

        Response response =
                given()
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("http://localhost:4000/api/secure/orders/5001");


        response.then()
                .statusCode(200)
                .body("id", equalTo(5001));
    }

    @Test
    void  createOrder() {

        String token = TokenManager.getToken();

        String body = """
            {
              "items": [101, 107],
              "currency": "INR"
            }
            """;

        Response response =
                given()
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("http://localhost:4000/api/secure/orders");


        response.then()
                .statusCode(201)
                .body("status", equalTo("CREATED"));
    }
}

 