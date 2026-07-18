package sdet.com.security;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static sdet.com.support.TripStackConfig.BASE_URL;

public class TripStackSecurityTest {
    @Test
    void shouldRejectRequestWithoutToken() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .when()
                        .get("/api/bookings");

        assertThat(response.statusCode())
                .isEqualTo(401);
    }

    @Test
    void shouldRejectInvalidToken() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization","Bearer INVALID_TOKEN")
                        .when()
                        .get("/api/auth/me");

        assertThat(response.statusCode())
                .isEqualTo(401);
    }
    @Test
    void shouldRejectExpiredToken() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization","Bearer EXPIRED_TOKEN")
                        .when()
                        .get("/api/bookings");

        assertThat(response.statusCode())
                .isEqualTo(401);
    }
    @Test
    void shouldRejectSqlInjectionLogin() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email","' OR 1=1 --",
                                "password","anything"
                        ))
                        .when()
                        .post("/api/auth/login");

        assertThat(response.statusCode())
                .isIn(400,401);
    }
    @Test
    void shouldRejectXssInput() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email","<script>alert(1)</script>",
                                "password","Password123"
                        ))
                        .when()
                        .post("/api/auth/login");

        assertThat(response.statusCode())
                .isIn(403);
    }
    @Test
    void shouldRejectInvalidMethod() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .when()
                        .delete("/api/auth/login");

        assertThat(response.statusCode())
                .isIn(404,405);
    }
}
