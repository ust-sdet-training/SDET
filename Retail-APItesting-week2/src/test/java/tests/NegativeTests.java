package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import utils.TokenManager;

import static io.restassured.RestAssured.given;

public class NegativeTests extends BaseTest {

    @Test
    @DisplayName("Verify request without token returns 401")
    void verifyUnauthorizedAccess() {

        given()
                .contentType("application/json")
                .body("""
                    {
                      "items":[101]
                    }
                    """)

                .when()
                .post(
                        RequestSpecs.createOrderPath())

                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Verify viewer cannot create order")
    void verifyForbiddenAccess() {

        String viewerToken =
                TokenManager.getViewerAccessToken();

        given()
                .spec(
                        RequestSpecs.viewerAuthSpec(
                                viewerToken))
                .body("""
                        {
                          "items":[101]
                        }
                        """)

                .when()
                .post(
                        RequestSpecs.createOrderPath())

                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Verify invalid order returns 404")
    void verifyOrderNotFound() {

        String token =
                TokenManager.getOpsAccessToken();

        given()
                .spec(
                        RequestSpecs.opsAuthSpec(
                                token))

                .when()
                .get(
                        RequestSpecs.orderById(
                                999999L))

                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Verify expired token returns 401")
    void verifyExpiredTokenAccess() {

        String expiredToken =
                TokenManager.getExpiredAccessToken();

        given()
                .spec(
                        RequestSpecs.expiredAuthSpec(
                                expiredToken))

                .when()
                .post(
                        RequestSpecs.createOrderPath())

                .then()
                .statusCode(401);
    }
}