package API_FrameWork.service;

import API_FrameWork.Factory.RequestSpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.BookingRequest;
import API_FrameWork.models.BookingResponse;

import static io.restassured.RestAssured.given;

public class BookingService {

    public BookingResponse createBooking(BookingRequest request) {

        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(request)
                .when()
                .post(EndPoints.BOOKINGS)
                .then()
                .statusCode(201)
                .extract()
                .as(BookingResponse.class);
    }

    public BookingResponse payBooking(String bookingId) {

        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body("{}")
                .when()
                .post(EndPoints.PAY_BOOKING.replace("{id}", bookingId))
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    public BookingResponse confirmBooking(String bookingId) {

        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .when()
                .post(EndPoints.CONFIRM_BOOKING.replace("{id}", bookingId))
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }
}