package com.capstone.negatives;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

import com.capstone.clients.BookingApiClient;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AuthorizationNegativeTest {
  @Test
  public void shouldFailWhenNoAuthTokenIsProvided() {
    BookingApiClient client = new BookingApiClient();
    Response response = given()
        .spec(client.bookingSpec())
        .when()
        .get("/api/auth/me");

    response.then().statusCode(401).body("error", equalTo("unauthorized"));
  }
}
