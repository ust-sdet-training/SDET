package com.Capstone.api.client;
import com.Capstone.api.model.modelData.Booking;
import com.Capstone.api.support.SpecFactory.reqSpec;
import com.Capstone.api.support.SpecFactory.respSpec;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class BookingApiClient {
    private RequestSpecification auth(String token) {
        return reqSpec.authRequest(token);
    }
    public Booking holdBusSeat(String token, String busId, String seatId) {
        return given()
                .spec(auth(token))
                .body(Map.of(
                        "journeyType", "bus",
                        "inventoryId", busId,
                        "seatIds", List.of(seatId)))
                .post("/bookings")
                .then()
                .spec(respSpec.createdSuccess())
                .extract().as(Booking.class);
    }
    public Booking payPending(String token, String bookingId) {
        return given()
                .spec(auth(token))
                .body(Map.of(
                        "method", "card",
                        "cardToken", "tok_test_visa"))
                .post("/bookings/{id}/pay", bookingId)
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(Booking.class);
    }
    public Booking confirm(String token, String bookingId) {
        return given()
                .spec(auth(token))
                .post("/bookings/{id}/confirm", bookingId)
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(Booking.class);
    }
    public Booking[] listBooking(String token) {
        return given()
                .spec(auth(token))
                .get("/bookings")
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(Booking[].class);
    }
    public Booking getByPnr(String token, String pnr) {
        return given()
                .spec(auth(token))
                .get("/bookings/{pnr}", pnr)
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(Booking.class);
    }
    public void cancel(String token, String bookingId) {
        given()
                .spec(auth(token))
                .post("/bookings/{id}/cancel", bookingId)
                .then()
                .spec(respSpec.okSuccess());
    }
    public void assertCannotCancel(String token, String bookingId) {
        given()
                .spec(auth(token))
                .post("/bookings/{id}/cancel", bookingId)
                .then()
                .statusCode(403);
    }
    public void assertUnauthorized(String token) {
        given()
                .spec(auth(token))
                .get("/bookings")
                .then()
                .spec(respSpec.unauthorized())
                .body("error", equalTo("unauthorized"));
    }
}