package org.sdet.clients;

import io.restassured.response.Response;
import org.sdet.base.BaseAPI;
import org.sdet.constants.Endpoints;
import org.sdet.model.request.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseAPI {

    public Response login(LoginRequest request) {

        return given()
                .spec(requestSpec)
                .body(request)

                .when()
                .post(Endpoints.LOGIN)

                .then()
                .extract()
                .response();
    }

}