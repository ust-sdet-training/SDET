package com.apitesting.tests;

import com.apitesting.client.AuthClient;
import com.apitesting.client.BookingClient;
import com.apitesting.client.BusClient;
import com.apitesting.data.builder.CustomerBuilder;
import com.apitesting.data.model.Bus;
import com.apitesting.data.model.Customer;
import com.apitesting.data.testUser;
import com.apitesting.support.Report;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class BaseTest {

    protected AuthClient authClient;
    protected BookingClient bookingClient;
    protected BusClient busClient;
    protected String authToken;

    @BeforeEach
    void setUp() {

        Report.step("Starting API test setup");
        
        authClient = new AuthClient();
        bookingClient = new BookingClient();
        busClient = new BusClient();
    }

    @Test
    @DisplayName("Auth Testing")
    public void authTesting(){
       Report.step("Started the Auth Testing");

        Customer customer = CustomerBuilder
                .aCustomer()
                .named(testUser.user1Name())
            .build();
        Report.pass("Customer builded using Customer Builder");

        Response authResponse = authClient.login(customer.email(), customer.password());

        authResponse.then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/json/authresponse.schema.json"));
        Report.pass("Structure checked");

        authToken = authResponse.jsonPath().getString("token");
        

        assertNotNull(authToken, "Token is NULL");
        assertFalse(authToken.isBlank());

        Report.pass("Customer authentication successful");
    }

    @Test
    @DisplayName("Bus Get testing")
    public void busGet(){
       Report.step("Started the Bus Testing");

        Response busResponse = busClient.get();

        busResponse.then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Bus Seats Deck testing")
    public void busSeatsDeck(){
       Report.step("Started the Bus Seats Deck Testing");

       Response busResponse = busClient.get();

        busResponse.then()
            .statusCode(200);

        Bus bus = busResponse.jsonPath()
                .getObject("buses[0]", Bus.class);
        Report.step("First Bus added ");
        Report.step(bus.toString());



        Response seatDeckResponse = busClient.getSeat(bus.id());

        seatDeckResponse.then()
            .body(matchesJsonSchemaInClasspath("schema/json/busseatresponse.schema.json"));

        assertEquals(bus.id(), seatDeckResponse.jsonPath().getString("busId"));
        assertEquals(bus.operator(), seatDeckResponse.jsonPath().getString("operator"));
        assertNotNull(seatDeckResponse.jsonPath().getString("decks.lower.size()"));
        assertNotNull(seatDeckResponse.jsonPath().getString("decks.upper.size()"));
    }

    @Test
    @DisplayName("Booking Flow testing")
    public void bookingFlowTesting() {
        Report.step("Started the Booking Flow Testing");

        Customer customer = CustomerBuilder
                .aCustomer()
                .named(testUser.user1Name())
                .build();

        Response authResponse = authClient.login(customer.email(), customer.password());
        authResponse.then().statusCode(200);

        authToken = authResponse.jsonPath().getString("token");

        Response busResponse = busClient.get();
        busResponse.then().statusCode(200);

        // Bus bus = busResponse.jsonPath().getObject("buses[0]", Bus.class);

        // Response seatResponse = busClient.getSeat(bus.id());

        // String seatId = seatResponse.jsonPath().getString("decks.lower[0].seatId");
        // if (seatId == null || seatId.isBlank()) {
        //     seatId = seatResponse.jsonPath().getString("decks.upper[0].seatId");
        // }

        // Response bookingResponse = bookingClient.createBooking(
        //         authToken,
        //         java.util.Map.of(
        //                 "journeyType", "bus",
        //                 "inventoryId", bus.id(),
        //                 "seatIds", java.util.List.of(seatId),
        //                 "refundable", true,
        //                 "holdTtlSec", 120
        //         )
        // );

        // String bookingId = bookingResponse.jsonPath().getString("id");

        // Response payResponse = bookingClient.payBooking(authToken, bookingId);

        // Response confirmResponse = bookingClient.confirmBooking(authToken, bookingId);
    }
}