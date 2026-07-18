package API_FrameWork.service;

import API_FrameWork.Factory.SpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.BookingRequest;
import API_FrameWork.models.BookingResponse;

import static io.restassured.RestAssured.given;


public class BookingService {
    public BookingResponse createBooking(
            BookingRequest request,
            String token) {
        return given()
                .spec(SpecFactory.authorizedSpec(token))
                .body(request)
                .when()
                .post(EndPoints.CREATE_BOOKING)
                .then()
                .statusCode(201)
                .extract()
                .as(BookingResponse.class);
    }
    public BookingResponse payBooking(
            String bookingId,
            String token) {
        return given()
                .spec(SpecFactory.authorizedSpec(token))
                .body("{}")
                .when()
                .post(EndPoints.PAY_BOOKING.replace("{id}", bookingId))
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }
    public BookingResponse confirmBooking(
            String bookingId,
            String token) {
        return given()
                .spec(SpecFactory.authorizedSpec(token))
                .when()
                .post(EndPoints.CONFIRM_BOOKING.replace("{id}", bookingId))
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }
}