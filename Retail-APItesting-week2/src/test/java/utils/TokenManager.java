package utils;

import io.restassured.response.Response;
import models.request.LoginRequest;
import models.response.LoginResponse;
import specs.RequestSpecs;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class TokenManager {

    private static String customerToken;

    private static String opsAccessToken;

    private static String viewerAccessToken;

    private static String expiredAccessToken;

    private TokenManager() {
    }

    public static String getCustomerToken() {

        if (customerToken == null) {

            LoginRequest request =
                    new LoginRequest(
                            ConfigReader.getCustomerEmail(),
                            ConfigReader.getCustomerPassword()
                    );

            LoginResponse response =

                    given()
                            .spec(
                                    RequestSpecs.basicSpec())
                            .body(request)

                            .when()
                            .post("/api/login")

                            .then()
                            .statusCode(200)

                            .extract()
                            .as(LoginResponse.class);

            customerToken =
                    response.getToken();

            assertNotNull(
                    customerToken,
                    "Customer token should not be null");
        }

        return customerToken;
    }

    public static String getOpsAccessToken() {

        if (opsAccessToken == null) {

            opsAccessToken =
                    generateOAuthToken(
                            ConfigReader.getOpsClientId(),
                            ConfigReader.getOpsClientSecret()
                    );
        }

        return opsAccessToken;
    }

    public static String getViewerAccessToken() {

        if (viewerAccessToken == null) {

            viewerAccessToken =
                    generateOAuthToken(
                            ConfigReader.getViewerClientId(),
                            ConfigReader.getViewerClientSecret()
                    );
        }

        return viewerAccessToken;
    }

    public static String getExpiredAccessToken() {

        if (expiredAccessToken == null) {

            expiredAccessToken =
                    generateOAuthToken(
                            ConfigReader.getExpiredClientId(),
                            ConfigReader.getExpiredClientSecret()
                    );
        }

        return expiredAccessToken;
    }

    private static String generateOAuthToken(
            String clientId,
            String clientSecret) {

        Response response =

                given()
                        .contentType(
                                "application/x-www-form-urlencoded;charset=UTF-8")
                        .formParam(
                                "grant_type",
                                "client_credentials")
                        .formParam(
                                "client_id",
                                clientId)
                        .formParam(
                                "client_secret",
                                clientSecret)

                        .when()
                        .post("/api/oauth/token");

        String token =
                response.jsonPath()
                        .getString("access_token");

        assertNotNull(
                token,
                "OAuth access token should not be null");

        return token;
    }

    public static void clearTokens() {

        customerToken = null;
        opsAccessToken = null;
        viewerAccessToken = null;
        expiredAccessToken = null;
    }
}