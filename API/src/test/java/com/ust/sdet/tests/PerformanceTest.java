package com.ust.sdet;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private static String getValue(String key) {
        String env = System.getenv(key);
        if (env != null && !env.isBlank()) {
            return env;
        }
        return dotenv.get(key);
    }

    private static final String BASE_URL = getValue("BASE_URL");

    private static final String EMAIL = getValue("EMAIL");

    private static final String PASSWORD = getValue("PASSWORD");

    @Test
    void checkoutPerformanceGate() {
        String token = 
            given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                    {
                        "email": "%s",
                        "password": "%s"
                    }
                    """, EMAIL, PASSWORD))
            .when()
                .post(BASE_URL + "/auth/login")
            .then()
                .statusCode(200)
                .body("empId", equalTo("1010"))
                .body("role", equalTo("traveller"))
                .body("displayName", equalTo("Judy Joshi"))
                .extract()
                .path("token");

        String busId = 
            given()
                .contentType(ContentType.JSON)
                .queryParam("from", "AMD")
                .queryParam("to", "DEL")
                .queryParam("date", "2026-08-02")
            .when()
                .get(BASE_URL + "/buses")
            .then()
                .statusCode(200)
                .body("count", greaterThan(0))
                .extract()
                .path("buses[0].id");

        String seatId =
            given()
                .contentType(ContentType.JSON)
            .when()
                .get(BASE_URL + "/buses/{id}/seats", busId)
            .then()
                .statusCode(200)
                .body("busId", equalTo(busId))
                .extract()
                .path("decks.lower[0].seatId");
        
        String bookingId = 
            given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(String.format("""
                    {
                        "journeyType": "bus",
                        "inventoryId": "%s",
                        "seatIds": ["%s"],
                        "refundable": true,
                        "holdTtlSec": 120
                    }
                    """, busId, seatId))
            .when()
                .post(BASE_URL + "/bookings")
            .then()
                .statusCode(201)
                .body("pnr", equalTo(null))
                .body("empId", equalTo("1010"))
                .body("journeyType", equalTo("bus"))
                .body("inventoryId", equalTo(busId))
                .body("state", equalTo("HELD"))
                .body("seatIds", contains(seatId))
                .body("refundable", equalTo(true))
                .extract()
                .path("id");

        long responseTime =
                given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                .when()
                    .post(BASE_URL + "/bookings/{id}/pay", bookingId)
                .time();

        System.out.println("Checkout Response Time: " + responseTime + " ms");

        assertTrue(responseTime < 400, "Performance regression detected");

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
        .when()
            .post(BASE_URL + "/reset")
        .then()
            .statusCode(200)
            .body("emp", equalTo("1010"));
    }
}