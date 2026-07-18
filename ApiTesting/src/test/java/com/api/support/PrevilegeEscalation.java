package com.api.support;

import com.api.stepdefs.AuthenticationStep;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class PrevilegeEscalation {

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
}
