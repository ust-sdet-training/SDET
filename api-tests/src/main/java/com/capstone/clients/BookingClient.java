package com.capstone.clients;

import com.capstone.config.EnvConfig;
import com.capstone.models.BookingRequest;

import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookingClient {
  private final RequestSpecification spec;

  public BookingClient() {
    this.spec = new RequestSpecBuilder()
        .setBaseUri(EnvConfig.require("BASE_URL"))
        .setContentType(ContentType.JSON)
        .build();
  }

  public RequestSpecification spec() {
    return spec;
  }

  public Response createBooking(String token, BookingRequest request) {
    return given()
        .spec(spec)
        .header("Authorization", "Bearer " + token)
        .body(request)
        .when()
        .post("/api/bookings");
  }

  public Response getMyBookings(String token) {
    return given()
        .spec(spec)
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/bookings/me");
  }
}