package com.tripstack.utils;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class SchemaValidation {

    private SchemaValidation() {
    }

    public static void validateLogin(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/login-schema.json"));
    }

    public static void validateAuthMe(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/auth-me-schema.json"));
    }

    public static void validateFlightSearch(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/flight-search-schema.json"));
    }

    public static void validateSeatMap(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/seat-map-schema.json"));
    }

    public static void validateBooking(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

    public static void validateBookingList(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/booking-list-schema.json"));
    }

    public static void validatePayment(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/payment-schema.json"));
    }

    public static void validateError(Response response) {
        response.then().body(matchesJsonSchemaInClasspath("schemas/error-schema.json"));
    }

}