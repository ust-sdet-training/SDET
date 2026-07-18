package com.apitesting.tests;

import com.apitesting.client.BookingClient;
import com.apitesting.client.FlightClient;
import com.apitesting.data.builder.BookingBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.apitesting.data.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import com.apitesting.data.testUser;

public class SecurityTests extends BaseTest {

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
    void shouldNotCancelAnotherUsersBooking() {

        String otherToken = authClient
                .login("alice@tripstack.test", "Password@123")
                .jsonPath()
                .getString("token");

        String flightId = getFlightId();
        String seat = getAvailableSeat(flightId);

        Object booking = BookingBuilder.aBooking()
                .inventoryId(flightId)
                .seat(seat)
                .build();

        Response hold = bookingClient.createBooking(otherToken, booking);

        hold.then().statusCode(201);

        String bookingId = hold.jsonPath().getString("id");

        String myToken = authClient
                .login(testUser.EMAIL(), testUser.PASSWORD())
                .jsonPath()
                .getString("token");

        bookingClient.cancel(myToken, bookingId)
                .then()
                .log().all()
                .statusCode(403);
    }
}