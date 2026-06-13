package services;

import config.ConfigReader;

import static io.restassured.RestAssured.given;

public class AuthService {

    private AuthService() {
    }

    public static String getOpsAccessToken() {

        return given()
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded; charset=UTF-8"
                )
                .formParam(
                        "grant_type",
                        "client_credentials"
                )
                .formParam(
                        "client_id",
                        "retail-ops-client"
                )
                .formParam(
                        "client_secret",
                        ConfigReader.get("OAUTH_CLIENT_SECRET")
                )
                .when()
                .post("/api/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }

    public static String getViewerAccessToken() {

        return given()
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded; charset=UTF-8"
                )
                .formParam(
                        "grant_type",
                        "client_credentials"
                )
                .formParam(
                        "client_id",
                        "retail-viewer-client"
                )
                .formParam(
                        "client_secret",
                        ConfigReader.get("OAUTH_VIEWER_CLIENT_SECRET")
                )
                .when()
                .post("/api/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
}