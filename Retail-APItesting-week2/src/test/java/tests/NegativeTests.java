package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.TokenManager;

import static io.restassured.RestAssured.given;

public class NegativeTests extends BaseTest {

    @Test
    @DisplayName("Verify request without token returns 401")
    void verifyUnauthorizedAccess() {

        given()

                .when()
                .get("/api/secure/orders")

                .then()
                .spec(ResponseSpecs.unauthorizedSpec);
    }

    @Test
    @DisplayName("Verify viewer cannot allocate order")
    void verifyForbiddenAccess() {

        String viewerToken =
                TokenManager.getViewerAccessToken();

        long invalidOrderId = 1L;

        given()
                .spec(
                        RequestSpecs.viewerAuthSpec(
                                viewerToken))

                .when()
                .post(
                        "/api/secure/orders/"
                                + invalidOrderId
                                + "/allocate")

                .then()
                .spec(ResponseSpecs.forbiddenSpec);
    }

    @Test
    @DisplayName("Verify invalid order returns 404")
    void verifyOrderNotFound() {

        String token =
                TokenManager.getOpsAccessToken();

        long invalidOrderId = 999999L;

        given()
                .spec(
                        RequestSpecs.opsAuthSpec(
                                token))

                .when()
                .get(
                        "/api/secure/orders/"
                                + invalidOrderId)

                .then()
                .spec(ResponseSpecs.notFoundSpec);
    }

    @Test
    @DisplayName("Verify ship before allocate returns 409")
    void verifyInvalidStateTransition() {

        String token =
                TokenManager.getOpsAccessToken();

        long orderId = 999999L;

        given()
                .spec(
                        RequestSpecs.opsAuthSpec(
                                token))

                .when()
                .post(
                        "/api/secure/orders/"
                                + orderId
                                + "/ship")

                .then()
                .spec(ResponseSpecs.conflictSpec);
    }
    @Test
    @DisplayName("Verify request without token returns 401")
    void verifyUnauthorizedaccess() {

        given()

                .when()
                .get(
                        RequestSpecs.createOrderPath())

                .then()
                .spec(
                        ResponseSpecs.unauthorizedSpec);
    }

    @Test
    @DisplayName("Verify viewer cannot allocate order")
    void verifyForbiddenaccess() {

        String viewerToken =
                TokenManager.getViewerAccessToken();

        given()
                .spec(
                        RequestSpecs.viewerAuthSpec(
                                viewerToken))

                .when()
                .post(
                        RequestSpecs.allocateOrder(
                                1L))

                .then()
                .spec(
                        ResponseSpecs.forbiddenSpec);
    }

    @Test
    @DisplayName("Verify invalid order returns 404")
    void verifyOrderNotfound() {

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
                .spec(
                        ResponseSpecs.notFoundSpec);
    }
}