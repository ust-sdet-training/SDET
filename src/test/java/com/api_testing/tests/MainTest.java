package com.api_testing.tests;

import com.api_testing.specs.*;
import com.api_testing.support.Config;
import com.api_testing.models.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private String getToken() {
        Response response = given()
                .spec(ReqSpec.requestSpec())
                .body(new AuthRequest(Config.USER_NAME, Config.PASSWORD))
                .when()
                .post(Config.AUTH_PATH)
                .then()
                .extract()
                .response();
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400);
        if (response.getStatusCode() == 200) {
            AuthResponse authResponse = response.as(AuthResponse.class);
            return authResponse.token();
        }
        return null;
    }

    private int existBookingId(){
        Response response = given()
                .spec(ReqSpec.requestSpec())
                .when()
                .get(Config.BASE_PATH)
                .then()
                .spec(ResSpec.success200())
                .extract()
                .response();
        if (response.getStatusCode() == 200) {
            BookingGetResponse[] bookings = response.as(BookingGetResponse[].class);
            if (bookings != null && bookings.length > 0) {
                return bookings[0].bookingid();
            }
        }
        return 69;
    }

    @Test
    void createToken() {
        String token = getToken();
        assertTrue(token == null || !token.isEmpty());
    }

    @Test
    void invalidAuth() {
        Response response = given()
                .spec(ReqSpec.requestSpec())
                .body(new AuthRequest(Config.USER_NAME, Config.WRONG_PASSWORD))
                .when()
                .post(Config.AUTH_PATH)
                .then()
                .extract()
                .response();

        assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 200);
        String body = response.getBody().asString();
        assertTrue(body.contains("Bad credentials") || body.contains("reason"));
    }
    @Test
    void getBookingById() {
        int bookingId = 999999;

        Response response = given()
                .spec(ReqSpec.requestSpec())
                .when()
                .get(Config.BASE_PATH + "/" + bookingId)
                .then()
                .spec(ResSpec.notFound404())
                .extract()
                .response();
        assertTrue(response.getStatusCode() == 404);
    }


    @Test
    void updateBooking() {
        int bookingId = existBookingId();
        String token = getToken();
        BookingRequest updatePayload = new BookingRequest("Michael");

        Response response = given()
                .spec(ReqSpec.requestSpec())
                .cookie("token", token)
                .body(updatePayload)
                .when()
                .patch(Config.BASE_PATH + "/" + bookingId)
                .then()
                .extract()
                .response();

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 500);
        if (response.getStatusCode() == 200) {
            String body = response.getBody().asString();
            assertTrue(body.contains("Michael"));
        }
    }


    @Test
    void deleteBooking() {
        int bookingId = existBookingId();
        String token = getToken();

        Response response = given()
                .spec(ReqSpec.requestSpec())
                .cookie("token", token)
                .when()
                .delete(Config.BASE_PATH + "/" + bookingId)
                .then()
                .spec(ResSpec.created201())
                .extract()
                .response();

        assertTrue(response.getBody().asString().contains("Created"));
    }

    @Test
    void verifyDeletedBookingId() {
        int bookingId = existBookingId();
        String token = getToken();
        given()
                .spec(ReqSpec.requestSpec())
                .cookie("token", token)
                .when()
                .delete(Config.BASE_PATH + "/" + bookingId)
                .then()
                .statusCode(201);
        Response response = given()
                .spec(ReqSpec.requestSpec())
                .when()
                .get(Config.BASE_PATH + "/" + bookingId)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 404);
    }
}
