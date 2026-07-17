package com.api.support;

import com.api.clients.ApiSpec;
import com.api.stepdefs.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BaseApiTest {

    @Test
    public void makeTheUserAuthentication() {

        AuthenticationStep authStep = new AuthenticationStep();

        authStep.makeTheUserAuthentication()
                .then()
                .spec(ApiSpec.okResponse())
                .log().all();
    }

    @Test
    public void getCurrentIdentityForDave() {

        AuthenticationStep authStep = new AuthenticationStep();

        Response loginResponse = authStep.makeTheUserAuthentication();

        String token = loginResponse.jsonPath().getString("token");

        GetCurrentIdentityStep identityStep = new GetCurrentIdentityStep();

        identityStep.getCurrentIdentity(token)
                .then()
                .spec(ApiSpec.okResponse())
                .log().all();
    }

    @Test
    public void searchBusesBetweenCities() {

        BusStep busStep = new BusStep();

        busStep.searchBuses("BLR", "HYD", "2026-08-01")
                .then()
                .spec(ApiSpec.okResponse())
                .log().all();
    }

    @Test
    public void shouldReturnBusNotFoundForInvalidBusId() {

        BusStep busStep = new BusStep();

        busStep.getSeatMap("INVALID-BUS-ID")
                .then()
                .statusCode(404)
                .body("error", equalTo("bus_not_found"))
                .log().all();
    }

    @Test
    public void validateBusSeatMapResponse() {

        BusStep busStep = new BusStep();

        String busId = busStep.getFirstBusId(
                "BLR",
                "HYD",
                "2026-08-01"
        );

        busStep.getSeatMap(busId)
                .then()
                .statusCode(200)
                .body("busId", equalTo(busId))
                .body("layout", equalTo("deck"))
                .body("decks.lower", notNullValue())
                .body("decks.upper", notNullValue())
                .log().all();
    }

    @Test
    public void shouldCreateBookingSuccessfully() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        BookingStep bookingStep = new BookingStep();

        bookingStep.createBooking(
                        token,
                        "flight",
                        "FL-DELBLR-51",
                        List.of("12A"),
                        true,
                        120)
                .then()
                .statusCode(201)
                .body("state", equalTo("HELD"))
                .body("journeyType", equalTo("flight"))
                .log().all();
    }

    @Test
    public void shouldCreatePayAndConfirmBooking() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        BookingStep bookingStep = new BookingStep();

        Response bookingResponse = bookingStep.createBooking(
                token,
                "flight",
                "FL-DELBLR-51",
                List.of("12A"),
                true,
                120
        );

        String bookingId = bookingStep.getBookingId(bookingResponse);

        bookingStep.payBooking(token, bookingId)
                .then()
                .statusCode(200)
                .body("state", equalTo("PAYMENT_PENDING"));

        Response confirmResponse =
                bookingStep.confirmBooking(token, bookingId);

        confirmResponse
                .then()
                .statusCode(200)
                .body("state", equalTo("CONFIRMED"))
                .body("pnr", notNullValue())
                .log().all();
    }

    @Test
    public void shouldReturnInvalidSeats() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        BookingStep bookingStep = new BookingStep();

        bookingStep.createBooking(
                        token,
                        "flight",
                        "FL-DELBLR-51",
                        List.of(),
                        true,
                        120)
                .then()
                .statusCode(400)
                .body("error", equalTo("INVALID_SEATS"))
                .log().all();
    }

    @Test
    public void shouldListBookings() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        BookingStep bookingStep = new BookingStep();

        bookingStep.getBookings(token)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void shouldCancelBooking() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        new ResetStep()
                .resetNamespace(token);

        BookingStep bookingStep = new BookingStep();

        String bookingId = bookingStep.getBookingId(
                bookingStep.createBooking(
                        token,
                        "flight",
                        "FL-DELBLR-51",
                        List.of("7A"),
                        true,
                        120
                )
        );

        bookingStep.payBooking(token, bookingId);

        bookingStep.confirmBooking(token, bookingId);

        bookingStep.cancelBooking(token, bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("state", equalTo("REFUNDED"));
    }

    @Test
    public void shouldGetBookingByPnr() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        new ResetStep()
                .resetNamespace(token)
                .then()
                .statusCode(200);

        BookingStep bookingStep = new BookingStep();

        String bookingId = bookingStep.getBookingId(
                bookingStep.createBooking(
                        token,
                        "flight",
                        "FL-DELBLR-51",
                        List.of("12A"),
                        true,
                        120
                )
        );

        bookingStep.payBooking(token, bookingId)
                .then()
                .log().all();

        Response confirmResponse =
                bookingStep.confirmBooking(token, bookingId);

        confirmResponse.then().log().all();

        String pnr = confirmResponse.jsonPath().getString("pnr");

        System.out.println("PNR = " + pnr);

        bookingStep.getBookingByPnr(token, pnr)
                .then()
                .log().all()
                .statusCode(200);
    }


    @Test
    public void shouldResetCurrentUserNamespace() {

        AuthenticationStep authStep = new AuthenticationStep();

        String token = authStep.makeTheUserAuthentication()
                .jsonPath()
                .getString("token");

        ResetStep resetStep = new ResetStep();

        resetStep.resetNamespace(token)
                .then()
                .statusCode(200)
                .body("emp", equalTo("1004"))
                .body("purged", notNullValue())
                .log().all();
    }
}