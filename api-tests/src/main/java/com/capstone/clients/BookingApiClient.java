package com.capstone.clients;

import com.capstone.config.EnvConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BookingApiClient {
  private final RequestSpecification spec;

  public BookingApiClient() {
    this.spec = new RequestSpecBuilder()
        .setBaseUri(EnvConfig.require("BASE_URL"))
        .setContentType(ContentType.JSON)
        .build();
  }

  public RequestSpecification authSpec() {
    return spec;
  }

  public RequestSpecification bookingSpec() {
    return spec;
  }

  public String bookingPayload() {
    return "{\"route\":\"GOI->PUN\",\"journey\":\"Bus AC-semi deck\",\"travelDate\":\"28/07/2026\",\"employeeId\":\"E12\"}";
  }

  public String tamperedJwtPayload() {
    return "{\"token\":\"tampered-token\"}";
  }
}
