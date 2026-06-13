package tests;

import base.BaseTest;
import config.ConfigReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationTest extends BaseTest {

    @Test
    void customerLoginShouldSucceed() {

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "email":"%s",
                          "password":"%s"
                        }
                        """.formatted(
                        ConfigReader.get("CUSTOMER_EMAIL"),
                        ConfigReader.get("CUSTOMER_PASSWORD")
                ))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}