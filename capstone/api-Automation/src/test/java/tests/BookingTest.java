package tests;


import clients.BookingClient;

import com.fasterxml.jackson.databind.JsonNode;

import io.restassured.response.Response;

import models.BookingRequest;

import org.junit.jupiter.api.Test;

import utils.JsonReader;
import utils.TokenManager;


import static org.junit.jupiter.api.Assertions.*;


public class BookingTest {



    @Test
    void bookingTest(){

        JsonNode data =
                JsonReader.getData("booking");


        BookingRequest request =
                new BookingRequest(
                        data.get("flightId").asText(),
                        data.get("passengerId").asText(),
                        data.get("seatNumber").asText()
                );


        Response response =
                new BookingClient()
                        .createBooking(
                                TokenManager.getToken(),
                                request
                        );


        assertEquals(
                201,
                response.statusCode()
        );


        String pnr =
                response.jsonPath()
                        .getString("pnr");


        assertNotNull(pnr);

    }

}