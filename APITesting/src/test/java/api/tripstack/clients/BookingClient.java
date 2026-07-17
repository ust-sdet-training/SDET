package api.tripstack.clients;

import api.tripstack.constants.ApiRoutes;
import api.tripstack.models.BookingRequest;
import api.tripstack.utils.ResilienceUtils;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingClient {
    private final String token;

    public BookingClient(String token) { this.token = token; }

    public Response createHold(BookingRequest request) { return post(ApiRoutes.BOOKINGS, request); }
    public Response pay(String bookingId) { return post(ApiRoutes.payment(bookingId), Map.of()); }
    public Response confirm(String bookingId) { return post(ApiRoutes.confirmation(bookingId), Map.of()); }
    public Response list() { return authorised().get(ApiRoutes.BOOKINGS); }
    public Response reset() { return post(ApiRoutes.RESET, Map.of()); }


    private Response post(String path, Object body) {
        return ResilienceUtils.withRetry(
                () -> authorised().contentType("application/json").body(body).post(path),
                3,
                300,
                response -> response != null && response.getStatusCode() >= 500,
                throwable -> true
        );
    }
    private io.restassured.specification.RequestSpecification authorised() {
        return given().header("Authorization", "Bearer " + token);
    }
}
