package com.apitesting.tests;

import com.apitesting.client.BookingClient;
import com.apitesting.client.FlightClient;
import com.apitesting.data.builder.BookingBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.apitesting.data.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingTests extends BaseTest {

    private FlightClient flightClient;
    private BookingClient bookingClient;

    @BeforeEach
    void init() {
        flightClient = new FlightClient();
        bookingClient = new BookingClient();
    }

    private String getFlightId() {
        Response response = flightClient.searchFlights(TestData.FROM, TestData.TO, TestData.DATE, 1, TestData.CABIN);
        return response.jsonPath().getString("flights[0].id");
    }

    private String getAvailableSeat(String flightId) {
        Response response = flightClient.getSeatMap(flightId, TestData.CABIN);
        response.prettyPrint();
        return response.jsonPath()
                .getString("rows.find { it.seats.find { s -> s.occupied == false } }" +
                        ".seats.find { it.occupied == false }.seat_id");
    }


    @Test
    void shouldCreateBooking() {
        String flightId = getFlightId();
        String seat = getAvailableSeat(flightId);
        Object booking = BookingBuilder.aBooking()
                        .inventoryId(flightId)
                        .seat(seat)
                        .build();

        System.out.println("Flight ID = " + flightId);
        System.out.println("Seat = " + seat);
        Response response = bookingClient.createBooking(authToken, booking);
        response.then().log().all();
        response.then()
                .statusCode(201)
                .body("state", equalTo("HELD"))
                .body("inventoryId", equalTo(flightId))
                .body("seatIds[0]", equalTo(seat));
    }
    @Test
    void shouldPayForBooking() {
        String flightId = getFlightId();
        String seat = getAvailableSeat(flightId);
        Object booking =
                BookingBuilder.aBooking()
                        .inventoryId(flightId)
                        .seat(seat)
                        .build();

        Response hold = bookingClient.createBooking(authToken, booking);
        String bookingId = hold.jsonPath().getString("id");
        Response payment = bookingClient.pay(authToken, bookingId);
        payment.then()
                .statusCode(200)
                .body("state", equalTo("PAYMENT_PENDING"));
    }
    @Test
    void shouldConfirmBooking() {
        String flightId = getFlightId();
        String seat = getAvailableSeat(flightId);
        Object booking =
                BookingBuilder.aBooking()
                        .inventoryId(flightId)
                        .seat(seat)
                        .build();
        Response hold = bookingClient.createBooking(authToken, booking);
        String bookingId = hold.jsonPath().getString("id");
        bookingClient.pay(authToken, bookingId);
        Response confirm = bookingClient.confirm(authToken, bookingId);
        confirm.then().log().all();
        confirm.then()
                .statusCode(200)
                .body("state", equalTo("CONFIRMED"))
                .body("pnr", notNullValue());
    }
    @Test
    void shouldGetBookingByPnr() {
        String flightId = getFlightId();
        String seat = getAvailableSeat(flightId);
        Object booking =
                BookingBuilder.aBooking()
                        .inventoryId(flightId)
                        .seat(seat)
                        .build();

        Response hold = bookingClient.createBooking(authToken, booking);
        String bookingId = hold.jsonPath().getString("id");
        bookingClient.pay(authToken, bookingId);

        Response confirm = bookingClient.confirm(authToken, bookingId);
        confirm.then().log().all();
        String pnr = confirm.jsonPath().getString("pnr");

        Response response = bookingClient.getBooking(authToken, pnr);
        response.then().log().all();
        response.then()
                .statusCode(200)
                .body("pnr", equalTo(pnr))
                .body("state", equalTo("CONFIRMED"));
    }

}