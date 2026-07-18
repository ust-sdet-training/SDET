package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import com.tripstack.models.BookingRequest;
import com.tripstack.utils.TokenManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookingNegativeTests {

    static String token;
    static String busId;

    @BeforeAll
    static void setup() {

        BaseConfig.setup();
        token = TokenManager.getToken();

        Response bus = given()
                .queryParam("from", "HYD")
                .queryParam("to", "DEL")
                .get(EndPoints.BUSES);

        busId = bus.jsonPath().getString("buses[0].id");
    }

    @Test
    void emptySeatBooking() {

        BookingRequest request = new BookingRequest();
        request.setJourneyType("bus");
        request.setInventoryId(busId);
        request.setSeatIds(Collections.emptyList());

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(request)
                .when()
                .post(EndPoints.BOOKINGS)
                .then()
                .statusCode(400);
    }

    @Test
    void confirmWithoutPayment() {

        BookingRequest request = new BookingRequest();
        request.setJourneyType("bus");
        request.setInventoryId(busId);
        request.setSeatIds(List.of("L4"));

        Response hold = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(request)
                .when()
                .post(EndPoints.BOOKINGS);

        // Skip if seat is already booked
        if (hold.statusCode() == 409) {
            System.out.println("Seat already booked. Test skipped.");
            return;
        }

        assertEquals(201, hold.statusCode());

        String bookingId = hold.jsonPath().getString("id");
        assertNotNull(bookingId);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post(EndPoints.BOOKINGS + "/" + bookingId + "/confirm")
                .then()
                .statusCode(409);
    }
}