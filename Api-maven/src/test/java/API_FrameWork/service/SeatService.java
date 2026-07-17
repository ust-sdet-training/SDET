package API_FrameWork.service;

import API_FrameWork.Factory.RequestSpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.SeatMapResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SeatService {

    public SeatMapResponse getSeatMap(String flightId) {

        Response response =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .log().all()
                        .when()
                        .get(EndPoints.FLIGHT_SEATS.replace("{id}", flightId));

        System.out.println("========================================");
        System.out.println("Seat Map Response");
        System.out.println("========================================");

        response.prettyPrint();

        response.then().statusCode(200);

        return response.as(SeatMapResponse.class);
    }
}