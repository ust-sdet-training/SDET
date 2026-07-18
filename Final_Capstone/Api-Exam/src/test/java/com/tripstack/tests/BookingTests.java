package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import com.tripstack.models.BookingRequest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTests {

    static String token;
    static String busId;

    @BeforeAll
    static void setup() {

        BaseConfig.setup();
        token = TokenManager.getToken();

        Response bus = given()
                .queryParam("from", "HYD")
                .queryParam("to", "DEL")
                .when()
                .get(EndPoints.BUSES);

        assertEquals(200, bus.statusCode());

        busId = bus.jsonPath().getString("buses[0].id");
        assertNotNull(busId);
    }

    @Test
    void completeBusBookingFlow() {

        // Hold Booking
        BookingRequest request = new BookingRequest();
        request.setJourneyType("bus");
        request.setInventoryId(busId);
        request.setSeatIds(List.of("L3"));

        Response hold = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(request)
                .when()
                .post(EndPoints.BOOKINGS);

        hold.prettyPrint();
        assertEquals(201, hold.statusCode());

        String bookingId = hold.jsonPath().getString("id");
        assertNotNull(bookingId);

        // Pay
        Response pay = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post(EndPoints.BOOKINGS + "/" + bookingId + "/pay");

        pay.prettyPrint();

        if (pay.statusCode() == 402) {
            assertEquals("GATEWAY_DECLINE",
                    pay.jsonPath().getString("error"));
            return;
        }

        assertEquals(200, pay.statusCode());

        // Confirm
        Response confirm = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post(EndPoints.BOOKINGS + "/" + bookingId + "/confirm");

        confirm.prettyPrint();
        assertEquals(200, confirm.statusCode());

        String pnr = confirm.jsonPath().getString("pnr");
        assertNotNull(pnr);
        assertTrue(pnr.startsWith("TS-1020"));

        // My Bookings
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(EndPoints.BOOKINGS)
                .then()
                .statusCode(200);

        // Get Booking by PNR
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(EndPoints.BOOKINGS + "/" + pnr)
                .then()
                .statusCode(200);

        // Cancel
        Response cancel = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post(EndPoints.BOOKINGS + "/" + bookingId + "/cancel");
        assertEquals(200, cancel.statusCode());
    }
}