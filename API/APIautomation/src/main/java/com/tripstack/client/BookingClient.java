package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingClient {

    private final String BASE_URL =
            ConfigManager.BASE_URL;

    public Response createBooking(
            String token,
            String journeyType,
            String inventoryId,
            List<String> seatIds,
            boolean refundable
    ) {

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .body(
                        """
                        {
                          "journeyType":"%s",
                          "inventoryId":"%s",
                          "seatIds":%s,
                          "refundable":%s
                        }
                        """.formatted(
                                journeyType,
                                inventoryId,
                                seatIds.toString(),
                                refundable
                        )
                )
                .log().all()
                .when()
                .post("/api/bookings")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response payBooking(
            String token,
            String bookingId
    ) {

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .body("{}")
                .when()
                .post("/api/bookings/" + bookingId + "/pay");
    }

    public Response confirmBooking(
            String token,
            String bookingId
    ) {

        return given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .when()
                .post("/api/bookings/" + bookingId + "/confirm");
    }

    public Response cancelBooking(
            String token,
            String bookingId
    ) {

        return given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .when()
                .post("/api/bookings/" + bookingId + "/cancel");
    }

    public Response getMyBookings(
            String token
    ) {

        return given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .when()
                .get("/api/bookings");
    }

    public Response getBookingByPNR(
            String token,
            String pnr
    ) {

        return given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .when()
                .get("/api/bookings/" + pnr);
    }
}