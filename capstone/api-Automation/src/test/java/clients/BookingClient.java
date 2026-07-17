package clients;

import io.restassured.response.Response;
import models.BookingRequest;
import spec.RequestSpec;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response createBooking(String token, BookingRequest request) {

        return given()
                .spec(RequestSpec.request())
                .header("Authorization", "Bearer " + token)
                .body(request)
                .log().all()

                .when()
                .post(ConfigReader.get("booking.endpoint"))

                .then()
                .log().all()

                .extract()
                .response();
    }
}