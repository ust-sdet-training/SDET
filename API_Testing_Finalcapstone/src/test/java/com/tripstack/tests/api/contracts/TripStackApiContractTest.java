package com.tripstack.tests.api.contracts;

import com.tripstack.api.client.TripStackApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TripStackApiContractTest {
    private final TripStackApiClient apiClient = new TripStackApiClient();

    @Test
    void loginReturnsTokenAndIdentity() {
        Response loginResponse = apiClient.login("dave@tripstack.test", "Password@123");

        assertThat(loginResponse.getStatusCode(), equalTo(200));
        assertThat(loginResponse.jsonPath().getString("token"), not(emptyOrNullString()));
        assertThat(loginResponse.jsonPath().getString("empId"), not(emptyOrNullString()));

        String token = loginResponse.jsonPath().getString("token");
        Response meResponse = apiClient.currentIdentity(token);

        assertThat(meResponse.getStatusCode(), equalTo(200));
        assertThat(meResponse.jsonPath().getString("email"), equalTo("dave@tripstack.test"));
    }

    @Test
    void searchFlightsReturnsSearchResults() {
        Response response = apiClient.searchFlights("MAA", "HYD", "2026-07-25", 1, "business");

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("flights"), not(empty()));
        assertThat(response.jsonPath().getString("from"), equalTo("MAA"));
        assertThat(response.jsonPath().getString("to"), equalTo("HYD"));
    }

    @Test
    void bookingFlowCreatesAndListsBooking() {
        Response loginResponse = apiClient.login("dave@tripstack.test", "Password@123");
        String token = loginResponse.jsonPath().getString("token");

        Response createResponse = apiClient.createBooking(token, "flight", "FL-DELBLR-51", new String[]{"12A"}, true);
        assertThat(createResponse.getStatusCode(), equalTo(201));
        assertThat(createResponse.jsonPath().getString("state"), equalTo("HELD"));

        Response payResponse = apiClient.payBooking(token, createResponse.jsonPath().getString("id"));
        assertThat(payResponse.getStatusCode(), equalTo(200));
        assertThat(payResponse.jsonPath().getString("state"), equalTo("PAYMENT_PENDING"));

        Response confirmResponse = apiClient.confirmBooking(token, createResponse.jsonPath().getString("id"));
        assertThat(confirmResponse.getStatusCode(), equalTo(200));
        assertThat(confirmResponse.jsonPath().getString("state"), equalTo("CONFIRMED"));

        Response listingResponse = apiClient.listBookings(token);
        assertThat(listingResponse.getStatusCode(), equalTo(200));
        assertThat(listingResponse.jsonPath().getList("$"), not(empty()));
    }
}
