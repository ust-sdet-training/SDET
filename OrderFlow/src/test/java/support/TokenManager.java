package support;

import config.ConfigReader;
import services.AuthService;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;


    public static String getToken() {

        if (token == null) {
            token = new AuthService().getAccessToken();
        }

        return token;
    }

    public static String getViewerToken() {

        return given()
                .baseUri("http://localhost:4000")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("grant_type",    "client_credentials")
                .formParam("client_id",     "retail-viewer-client")
                .formParam("client_secret", "your-viewer-secret")
                .when()
                .post("/api/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
}