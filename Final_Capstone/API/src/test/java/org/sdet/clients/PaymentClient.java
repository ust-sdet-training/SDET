package org.sdet.clients;

import io.restassured.response.Response;
import org.sdet.base.BaseAPI;
import org.sdet.constants.Endpoints;
import org.sdet.utils.TokenManager;

import static io.restassured.RestAssured.given;

public class PaymentClient extends BaseAPI {

    public Response payBooking(String bookingId) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .pathParam("id", bookingId)

                .when()
                .post(Endpoints.PAY)

                .then()
                .extract()
                .response();
    }

}