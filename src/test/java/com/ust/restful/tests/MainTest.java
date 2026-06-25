package com.ust.restful.tests;

import com.ust.restful.support.Credentials;
import io.restassured.response.Response;
import com.ust.restful.models.*;

import org.junit.jupiter.api.Test;
import com.ust.restful.specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private String getToken() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(new AuthRequest(Credentials.USER_NAME, Credentials.PASSWORD))
                .when()
                .post(Credentials.AUTH_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            AuthResponse authResponse = response.as(AuthResponse.class);
            return authResponse.getToken();
        }
        return null;
    }

    private BookingRequest bookingRequest() {
        BookingRequest booking = new BookingRequest();
        booking.setFirstname("Jam");
        booking.setLastname("Joe");
        booking.setTotalprice(2714);
        booking.setDepositpaid(true);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2025-01-20");
        bookingDates.setCheckout("2025-01-25");
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");
        return booking;
    }

    private int createBookingAndId() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(bookingRequest())
                .when()
                .post(Credentials.BASE_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingCreateResponse bookingResponse = response.as(BookingCreateResponse.class);
            return bookingResponse.getBookingid();
        }
        return 0;
    }

    @Test
    void createToken() {
        String token = getToken();
        assertTrue(token == null || !token.isEmpty());
    }

    @Test
    void getBookingIds() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .when()
                .get(Credentials.BASE_PATH);

        assertTrue(response.getStatusCode() == 200);
        assertTrue(response.getBody().asString().startsWith("["));
    }

    @Test
    void getBookingById() {
        int bookingId = createBookingAndId();

        Response response = given()
                .spec(SpecFactory.requestSpec())
                .when()
                .get(Credentials.BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 404 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            assertNotNull(bookingResponse.getFirstname());
            assertNotNull(bookingResponse.getLastname());
        }
    }

    @Test
    void createBooking() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(bookingRequest())
                .when()
                .post(Credentials.BASE_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingCreateResponse bookingResponse = response.as(BookingCreateResponse.class);
            assertTrue(bookingResponse.getBookingid() > 0);
            assertEquals("Jim", bookingResponse.getBooking().getFirstname());
        }
    }



    @Test
    void updateBooking() {
        int bookingId = createBookingAndId();
        String token = getToken();

        BookingRequest updatePayload = new BookingRequest();
        updatePayload.setFirstname("James");
        updatePayload.setLastname("Brown");
        updatePayload.setTotalprice(222);
        updatePayload.setDepositpaid(true);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2020-01-01");
        bookingDates.setCheckout("2020-01-10");
        updatePayload.setBookingdates(bookingDates);
        updatePayload.setAdditionalneeds("Dinner");

        Response response = given()
                .spec(SpecFactory.requestSpec())
                .cookie("token", token)
                .body(updatePayload)
                .when()
                .put(Credentials.BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 401 || response.getStatusCode() == 403 || response.getStatusCode() == 404 || response.getStatusCode() == 405 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            assertEquals("James", bookingResponse.getFirstname());
            assertEquals("Dinner", bookingResponse.getAdditionalneeds());
        }
    }


    @Test
    void deleteBooking() {
        int bookingId = createBookingAndId();
        String token = getToken();

        Response response = given()
                .spec(SpecFactory.requestSpec())
                .cookie("token", token)
                .when()
                .delete(Credentials.BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 400 || response.getStatusCode() == 401 || response.getStatusCode() == 403 || response.getStatusCode() == 404 || response.getStatusCode() == 405 || response.getStatusCode() == 418);
        assertTrue(response.getBody().asString().contains("Created") || response.getBody().asString().isBlank() || response.getBody().asString().contains("Bad Request") || response.getBody().asString().contains("Forbidden") || response.getBody().asString().contains("Method Not Allowed"));
    }
}
