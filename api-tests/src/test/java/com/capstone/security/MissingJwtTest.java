package com.capstone.security;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class MissingJwtTest {
  @Test
  public void shouldRejectMissingJwt() {
    Response response = given()
        .when()
        .get("/api/auth/me");

    response.then().statusCode(401).body("error", equalTo("unauthorized"));
  }
}
