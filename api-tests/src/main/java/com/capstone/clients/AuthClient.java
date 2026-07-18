package com.capstone.clients;

import com.capstone.config.EnvConfig;
import com.capstone.models.LoginRequest;
import com.capstone.models.LoginResponse;

import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class AuthClient {
  private final RequestSpecification spec;

  public AuthClient() {
    this.spec = new RequestSpecBuilder()
        .setBaseUri(EnvConfig.require("BASE_URL"))
        .setContentType(ContentType.JSON)
        .build();
  }

  public RequestSpecification spec() {
    return spec;
  }

  public String login(String email, String password) {
    LoginResponse response = given()
        .spec(spec)
        .body(new LoginRequest(email, password))
        .when()
        .post("/api/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .as(LoginResponse.class);

    return response.getToken();
  }
}