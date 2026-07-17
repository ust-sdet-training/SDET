package tests;


import clients.BookingClient;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.response.Response;

import models.BookingRequest;

import org.junit.jupiter.api.Test;

import utils.JsonReader;
import utils.TokenManager;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BookingTest {



    @Test
    void bookingTest() {

        JsonNode data =
                JsonReader.getData("booking");


        BookingRequest request =
                new BookingRequest(
                        data.get("journeyType").asText(),
                        data.get("inventoryId").asText(),
                        List.of(data.get("seatIds").get(0).asText()),
                        data.get("refundable").asBoolean(),
                        data.get("holdTtlSec").asInt()
                );


        Response response = new BookingClient()
                .createBooking(TokenManager.getToken(), request);

        assertEquals(201, response.statusCode());

        assertNotNull(response.jsonPath().getString("id"));
        assertEquals("HELD", response.jsonPath().getString("state"));
        assertEquals("flight", response.jsonPath().getString("journeyType"));
        assertNull(response.jsonPath().getString("pnr"));
    }

}