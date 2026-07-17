package sdet.com.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TripStackNegativeTest {

    private static final String BASE_URL = "https://tripstack.doomple.com";

    @Test
    void shouldNotLoginWithWrongPassword() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email", "dave@tripstack.test",
                                "password", "WrongPassword"
                        ))
                        .when()
                        .post("/api/auth/login");

        assertThat(response.statusCode()).isEqualTo(401);

        assertThat(response.jsonPath().getString("error"))
                .isEqualTo("invalid_credentials");
    }
    @Test
    void shouldRejectBookingWithoutToken() {

        Map<String, Object> body = new HashMap<>();

        body.put("journeyType", "bus");
        body.put("inventoryId", "BUS-DELIXC-01");
        body.put("seatIds", List.of("L3"));
        body.put("refundable", true);
        body.put("holdTtlSec", 120);

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/api/bookings");

        assertThat(response.statusCode()).isEqualTo(401);

        assertThat(response.jsonPath().getString("error"))
                .isEqualTo("unauthorized");
    }
    @Test
    void shouldRejectInvalidToken() {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization", "Bearer abc123")
                        .when()
                        .get("/api/auth/me");

        assertThat(response.statusCode()).isEqualTo(401);
    }
//    @Test
//    void shouldNotCreateBookingWithoutSeat() {
//
//        String token =
//                given()
//                        .baseUri(BASE_URL)
//                        .contentType(ContentType.JSON)
//                        .body(Map.of(
//                                "email", "dave@tripstack.test",
//                                "password", "Password@123"
//                        ))
//                        .post("/api/auth/login")
//                        .jsonPath()
//                        .getString("token");
//
//        Response response =
//                given()
//                        .baseUri(BASE_URL)
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(ContentType.JSON)
//                        .body(Map.of(
//                                "journeyType", "bus",
//                                "inventoryId", "BUS-DELIXC-01",
//                                "seatIds", java.util.List.of(),
//                                "refundable", true,
//                                "holdTtlSec", 120
//                        ))
//                        .when()
//                        .post("/api/bookings");
//
//        assertThat(response.statusCode()).isEqualTo(400);
//
//        assertThat(response.jsonPath().getString("error"))
//                .isEqualTo("BAD_REQUEST");
//    }
    @Test
    void shouldReturn404ForInvalidBus() {

        String token =
                given()
                        .baseUri(BASE_URL)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email", "dave@tripstack.test",
                                "password", "Password@123"
                        ))
                        .post("/api/auth/login")
                        .jsonPath()
                        .getString("token");

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/buses/BUS999/seats");

        assertThat(response.statusCode()).isEqualTo(404);
    }
}