package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import api.config.Endpoints;
import api.specs.RequestSpecs;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;

public class SecurityApiTest {

    @Test
    void viewerCannotAccessAdminPing() {
        JsonPath authResponse = ApiClient.login(AppConfig.USER_EMAIL, AppConfig.USER_PASSWORD);
        String token = authResponse.getString("token");
        String role = authResponse.getString("role");


        assertThat("User should not be admin for this test", role, not(equalTo("admin")));


        given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .when()
            .get(Endpoints.AUTH_ADMIN_PING)
            .then()
            .statusCode(403);
    }
}
