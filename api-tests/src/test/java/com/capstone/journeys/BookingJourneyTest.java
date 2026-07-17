package com.capstone.journeys;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import com.capstone.clients.BookingApiClient;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class BookingJourneyTest {
  @Test
  public void shouldCreateBookingJourney() {
    BookingApiClient client = new BookingApiClient();
    Response response = given()
        .spec(client.authSpec())
        .body(client.bookingPayload())
        .when()
        .post("/api/bookings");

    response.then().statusCode(200).body("status", notNullValue());
  }
}
