package com.ust.capstone.stepdefs;

import com.ust.capstone.api.BookingClient;
import com.ust.capstone.bdd.WorldContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingSteps {

    private final WorldContext context;
    private final BookingClient bookingClient = new BookingClient();

    public BookingSteps(WorldContext context) {
        this.context = context;
    }

    @When("he requests his bookings")
    public void heRequestsHisBookings() {

        Response response = bookingClient.getBookings(
                context.getToken()
        );

        context.setResponse(response);
        context.setResponseStatus(response.getStatusCode());
    }

    @And("every booking should belong to employee {string}")
    public void everyBookingShouldBelongToEmployee(String empId) {

        List<Map<String, Object>> bookings =
                context.getResponse()
                        .jsonPath()
                        .getList("$");

        bookings.forEach(booking ->
                assertEquals(empId, booking.get("empId"))
        );
    }
}