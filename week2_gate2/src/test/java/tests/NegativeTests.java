package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.TokenManager;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeTests extends BaseTest {

    @Test
    @DisplayName("Reject request when access token is missing")
    void rejectRequestWithoutAccessToken() {

        Response response =
                given()
                        .when()
                        .get("/api/secure/orders/1");

        assertEquals(
                401,
                response.statusCode()
        );

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/error-schema.json"
                ));
    }

    @Test
    @DisplayName("Reject request when access token is invalid")
    void rejectRequestWithInvalidAccessToken() {

        Response response =
                given()
                        .header(
                                "Authorization",
                                "Bearer invalid-token"
                        )
                        .when()
                        .get("/api/secure/orders/1");

        assertEquals(
                401,
                response.statusCode()
        );

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/error-schema.json"
                ));
    }

    @Test
    @DisplayName("Return not found when order does not exist")
    void returnNotFoundForUnknownOrder() {

        Response response =
                given()
                        .header(
                                "Authorization",
                                "Bearer " + TokenManager.opsToken()
                        )
                        .when()
                        .get("/api/secure/orders/999999");

        assertEquals(
                404,
                response.statusCode()
        );

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/error-schema.json"
                ));
    }

    @Test
    @DisplayName("Reject malformed authorization header")
    void rejectMalformedAuthorizationHeader() {

        Response response =
                given()
                        .header(
                                "Authorization",
                                "InvalidHeader"
                        )
                        .when()
                        .get("/api/secure/orders/1");

        assertEquals(
                401,
                response.statusCode()
        );

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/error-schema.json"
                ));
    }

    @Test
    @DisplayName("Viewer role cannot create order")
    void viewerRoleCannotCreateOrder() {

        String viewerToken =
                TokenManager.viewerToken();

        Response response =
                given()
                        .header(
                                "Authorization",
                                "Bearer " + viewerToken
                        )
                        .contentType(ContentType.JSON)
                        .body("""
                                {
                                  "items":[101,102]
                                }
                                """)
                        .when()
                        .post("/api/secure/orders");

        assertEquals(
                403,
                response.statusCode()
        );

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/error-schema.json"
                ));
    }
}