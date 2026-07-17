package com.api.support;

import com.api.clients.ApiSpec;
import com.api.stepdefs.AuthenticationStep;
import com.api.stepdefs.BusStep;
import com.api.stepdefs.GetCurrentIdentityStep;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

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
}