package com.capstone.support;

import com.capstone.clients.AuthClient;
import com.capstone.clients.BookingClient;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class BaseApiTest {
  protected final AuthClient authClient = new AuthClient();
  protected final BookingClient bookingClient = new BookingClient();

  protected Response postJson(String path, Object body) {
    return given().spec(bookingClient.spec()).body(body).when().post(path);
  }
}