package api.api;

import api.config.Endpoints;
import api.specs.RequestSpecs;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class ApiClient {

    private ApiClient() {
        // no instantiation
    }

    public static JsonPath login(String email, String password) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
            .when()
            .post(Endpoints.AUTH_LOGIN)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static Response loginRaw(String email, String password) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
            .when()
            .post(Endpoints.AUTH_LOGIN)
            .andReturn();
    }

    public static JsonPath searchBuses(String from, String to, String date) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .queryParam("from", from)
            .queryParam("to", to)
            .queryParam("date", date)
            .when()
            .get(Endpoints.BUSES_SEARCH)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static JsonPath getBusSeats(String busId) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .when()
            .get(Endpoints.BUS_SEATS, busId)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static Response createBooking(String token, String payload) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .body(payload)
            .when()
            .post(Endpoints.BOOKINGS)
            .andReturn();
    }

    public static JsonPath payBooking(String token, String bookingId) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .body("{}")
            .when()
            .post(Endpoints.BOOKING_PAY, bookingId)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static Response payBookingRaw(String token, String bookingId) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .body("{}")
            .when()
            .post(Endpoints.BOOKING_PAY, bookingId)
            .andReturn();
    }

    public static JsonPath confirmBooking(String token, String bookingId) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .body("{}")
            .when()
            .post(Endpoints.BOOKING_CONFIRM, bookingId)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static JsonPath getBookingByPnr(String token, String pnr) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .when()
            .get(Endpoints.BOOKING_BY_PNR, pnr)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath();
    }

    public static JsonPath getAdminPing(String token) {
        return given()
            .spec(RequestSpecs.defaultSpec())
            .auth().oauth2(token)
            .when()
            .get(Endpoints.AUTH_ADMIN_PING)
            .then()
            .extract()
            .jsonPath();
    }
}
