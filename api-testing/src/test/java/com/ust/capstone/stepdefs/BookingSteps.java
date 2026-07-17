package com.ust.capstone.stepdefs;

import com.ust.capstone.api.AuthClient;
import com.ust.capstone.api.BookingClient;
import com.ust.capstone.api.model.LoginResponse;
import com.ust.capstone.bdd.WorldContext;
import com.ust.capstone.data.TestUsers;
import com.ust.capstone.data.secret.Secrets;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingSteps {

    private final WorldContext context;
    private final BookingClient bookingClient = new BookingClient();

    public BookingSteps(WorldContext context) {
        this.context = context;
    }

    @When("he requests his bookings")
    public void heRequestsHisBookings() {
        Response response = bookingClient.getBookings(context.getToken());

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

    @Given("{string} has a confirmed booking")
    public void hasAConfirmedBooking(String user) {

        Credentials credentials = credentialsFor(user);

        // login as Dave
        String token = AuthClient.login(
                credentials.email(),
                credentials.password()
        ).as(LoginResponse.class).token();

        // create booking (HELD)
        Response holdResponse = bookingClient.createBooking(
                token,
                """
                {
                  "journeyType":"bus",
                  "inventoryId":"BUS-BOMDEL-1",
                  "seatIds":["L3"]
                }
                """
        );

        String bookingId = holdResponse.jsonPath().getString("id");

        // pay
        bookingClient.payBooking(token, bookingId);

        // confirm
        Response confirmResponse =
                bookingClient.confirmBooking(token, bookingId);

        context.setOtherUserBookingId(
                confirmResponse.jsonPath().getString("id")
        );
    }

    @When("Bob cancels Dave's booking")
    public void bobCancelsDavesBooking() {

        Response response = bookingClient.cancelBooking(
                context.getToken(),
                context.getOtherUserBookingId()
        );

        context.setResponse(response);
        context.setResponseStatus(response.getStatusCode());
    }

    @And("the error should be {string}")
    public void theErrorShouldBe(String expectedError) {

        context.getResponse()
                .then()
                .body("error", equalTo(expectedError));
    }

    private Credentials credentialsFor(String user) {
        return switch (user.toLowerCase()) {
            case "bob" -> new Credentials(
                    TestUsers.BOB_EMAIL,
                    Secrets.get("TRIPSTACK_BOB_PASSWORD")
            );

            case "dave" -> new Credentials(
                    TestUsers.DAVE_EMAIL,
                    Secrets.get("TRIPSTACK_BOB_PASSWORD")
            );
            default -> throw new IllegalArgumentException(
                    "Unknown user: " + user
            );

        };
    }

    private record Credentials(String email, String password) {
    }
}