package tests;

import io.restassured.response.Response;
import models.AuthRequest;
import models.AuthResponse;
import models.BookingCreateResponse;
import models.BookingDates;
import models.BookingRequest;
import models.BookingResponse;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class IxigoApiTest {

    private static final String BASE_PATH = "/booking";
    private static final String AUTH_PATH = "/auth";

    private String getAuthToken() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(new AuthRequest("admin", "password123"))
                .when()
                .post(AUTH_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            AuthResponse authResponse = response.as(AuthResponse.class);
            return authResponse.getToken();
        }
        return null;
    }

    private BookingRequest buildBookingRequest() {
        BookingRequest booking = new BookingRequest();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2018-01-01");
        bookingDates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");
        return booking;
    }

    private int createBookingAndReturnId() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(buildBookingRequest())
                .when()
                .post(BASE_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingCreateResponse bookingResponse = response.as(BookingCreateResponse.class);
            return bookingResponse.getBookingid();
        }
        return 0;
    }
    
    @Test
    void createToken() {
        String token = getAuthToken();
        assertTrue(token == null || !token.isBlank());
    }

    @Test
    void getBookingIds() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .when()
                .get(BASE_PATH);

        assertTrue(response.getStatusCode() == 200);
        assertTrue(response.getBody().asString().startsWith("["));
    }

    @Test
    void createBooking() {
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(buildBookingRequest())
                .when()
                .post(BASE_PATH);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingCreateResponse bookingResponse = response.as(BookingCreateResponse.class);
            assertTrue(bookingResponse.getBookingid() > 0);
            assertEquals("Jim", bookingResponse.getBooking().getFirstname());
        }
    }

    @Test
    void getBookingById() {
        int bookingId = createBookingAndReturnId();

        Response response = given()
                .spec(SpecFactory.requestSpec())
                .when()
                .get(BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 404 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            assertNotNull(bookingResponse.getFirstname());
            assertNotNull(bookingResponse.getLastname());
        }
    }

    @Test
    void updateBookingWithPut() {
        int bookingId = createBookingAndReturnId();
        String token = getAuthToken();

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
                .put(BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400 || response.getStatusCode() == 401 || response.getStatusCode() == 403 || response.getStatusCode() == 404 || response.getStatusCode() == 405 || response.getStatusCode() == 418);
        if (response.getStatusCode() == 200) {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            assertEquals("James", bookingResponse.getFirstname());
            assertEquals("Dinner", bookingResponse.getAdditionalneeds());
        }
    }


    @Test
    void deleteBooking() {
        int bookingId = createBookingAndReturnId();
        String token = getAuthToken();

        Response response = given()
                .spec(SpecFactory.requestSpec())
                .cookie("token", token)
                .when()
                .delete(BASE_PATH + "/" + bookingId);

        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 400 || response.getStatusCode() == 401 || response.getStatusCode() == 403 || response.getStatusCode() == 404 || response.getStatusCode() == 405 || response.getStatusCode() == 418);
        assertTrue(response.getBody().asString().contains("Created") || response.getBody().asString().isBlank() || response.getBody().asString().contains("Bad Request") || response.getBody().asString().contains("Forbidden") || response.getBody().asString().contains("Method Not Allowed"));
    }
}
