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

        busStep.searchBuses("CCU", "DEL", "2026-07-29")
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
                "CCU",
                "DEL",
                "2026-07-29"
        );

        busStep.getSeatMap(busId)
                .then()
                .spec(ApiSpec.okResponse())
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

        new ResetStep()
                .resetNamespace(token)
                .then()
                .statusCode(200);


        BookingStep bookingStep = new BookingStep();

        bookingStep.createBooking(
                        token,
                        "bus",
                        "BUS-CCUDEL-02",
                        List.of("S1"),
                        true,
                        120)
                .then()
                .spec(ApiSpec.createdResponse())
                .body("state", equalTo("HELD"))
                .body("journeyType", equalTo("bus"))
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
                "bus",
                "BUS-CCUDEL-02",
                List.of("S1"),
                true,
                120
        );

        String bookingId = bookingStep.getBookingId(bookingResponse);

        bookingStep.payBooking(token, bookingId)
                .then()
                .spec(ApiSpec.okResponse())
                .body("state", equalTo("PAYMENT_PENDING"));

        Response confirmResponse =
                bookingStep.confirmBooking(token, bookingId);

        confirmResponse
                .then()
                .spec(ApiSpec.okResponse())
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
                        "bus",
                        "BUS-CCUDEL-02",
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
                .spec(ApiSpec.okResponse())
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
                        "bus",
                        "BUS-CCUDEL-02",
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
                .spec(ApiSpec.okResponse())
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
                .spec(ApiSpec.okResponse());

        BookingStep bookingStep = new BookingStep();

        String bookingId = bookingStep.getBookingId(
                bookingStep.createBooking(
                        token,
                        "bus",
                        "BUS-CCUDEL-02",
                        List.of("S1"),
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
                .spec(ApiSpec.okResponse());
    }

    @Test
    public void shouldReturnForbiddenForTravellerToken() {

        AuthenticationStep authStep =
                new AuthenticationStep();

        String token =
                authStep.makeTheUserAuthentication()
                        .jsonPath()
                        .getString("token");

        authStep.adminPing(token)
                .then()
                .statusCode(403)
                .body("error", equalTo("forbidden"))
                .body("required[0]", equalTo("admin"))
                .body("role", equalTo("traveller"))
                .log().all();
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
                .spec(ApiSpec.okResponse())
                .body("emp", equalTo("1008"))
                .body("purged", notNullValue())
                .log().all();
    }
}