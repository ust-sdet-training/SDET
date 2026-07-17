package com.travel.tests;

import com.travel.clients.AuthClient;
import com.travel.clients.BookingClient;
import com.travel.clients.FlightClient;
import com.travel.models.request.BookingRequest;
import com.travel.models.request.LoginRequest;
import com.travel.models.response.BookingResponse;
import com.travel.models.response.Flight;
import com.travel.models.response.Seat;
import com.travel.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TripStackAPITests {

    private static final AuthClient authClient = new AuthClient();
    private static final FlightClient flightClient = new FlightClient();
    private static final BookingClient bookingClient = new BookingClient();

    @BeforeAll
    static void setup() {

        ConfigReader.loadConfig();

        RestAssured.baseURI = ConfigReader.get("BASE_URI");
        RestAssured.basePath = ConfigReader.get("BASE_PATH");
        RestAssured.useRelaxedHTTPSValidation();
    }

    @AfterAll
    static void tearDown() {
        RestAssured.reset();
    }

    @Test
    @DisplayName("Customer can complete flight booking lifecycle")
    void customerCanBookFlight() {

        String token = authClient.token();

        Flight flight = flightClient.firstFlight(ConfigReader.get("FROM"), ConfigReader.get("TO"));

        Seat seat = flightClient.firstAvailableSeat(flight.id());

        BookingRequest bookingRequest = new BookingRequest(ConfigReader.get("JOURNEY_TYPE"), flight.id(), List.of(seat.seatId()), Boolean.parseBoolean(ConfigReader.get("REFUNDABLE")));

        BookingResponse booking = bookingClient.hold(token, bookingRequest);

        assertThat(booking.state(), equalTo("HELD"));

        BookingResponse payment = bookingClient.pay(token, booking.id());

        assertThat(payment.state(), equalTo("PAYMENT_PENDING"));

        BookingResponse confirmed = bookingClient.confirm(token, booking.id());

        assertThat(confirmed.state(), equalTo("CONFIRMED"));

        assertThat(confirmed.pnr(), notNullValue());

        BookingResponse byPnr = bookingClient.byPnr(token, confirmed.pnr());

        assertThat(byPnr.pnr(), equalTo(confirmed.pnr()));

        BookingResponse cancelled = bookingClient.cancel(token, booking.id());

        assertThat(cancelled.state(), equalTo("REFUNDED"));

    }

    @Test
    @DisplayName("Login fails with invalid credentials")
    void loginWithInvalidCredentials() {

        LoginRequest request = new LoginRequest("invalid@tripstack.com", "wrongPassword");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Cancelling booking twice returns conflict")
    void cancelBookingTwice() {

        String token = authClient.token();

        Flight flight = flightClient.firstFlight(ConfigReader.get("FROM"), ConfigReader.get("TO"));

        Seat seat = flightClient.firstAvailableSeat(flight.id());

        BookingRequest request = new BookingRequest(
                ConfigReader.get("JOURNEY_TYPE"),
                flight.id(),
                List.of(seat.seatId()),
                Boolean.parseBoolean(ConfigReader.get("REFUNDABLE"))
        );

        BookingResponse booking = bookingClient.hold(token, request);

        bookingClient.pay(token, booking.id());

        bookingClient.confirm(token, booking.id());

        bookingClient.cancel(token, booking.id());

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .post("/bookings/" + booking.id() + "/cancel")
                .then()
                .statusCode(409);
    }

    @Test
    @DisplayName("Get flights response matches JSON schema")
    void getFlightsSchemaValidation() {

        given()
                .queryParam("from", ConfigReader.get("FROM"))
                .queryParam("to", ConfigReader.get("TO"))
                .queryParam("date", String.valueOf(LocalDate.now().plusDays(9)))
                .queryParam("pax", ConfigReader.get("PAX"))
                .queryParam("cls", ConfigReader.get("CLASS"))
                .when()
                .get("/flights")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/get_flights_schema.json"));
    }

}