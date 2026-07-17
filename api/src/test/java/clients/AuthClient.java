package clients;


import constants.ApiEndpoints;
import models.LoginRequest;
import models.LoginResponse;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;



public class AuthClient extends BaseClient {


    public LoginResponse login(
            LoginRequest request
    ){


        Response response =

                given()

                        .spec(
                                request()
                        )

                        .body(request)

                        .when()

                        .post(
                                ApiEndpoints.LOGIN
                        )

                        .then()

                        .spec(
                                specs.SpecFactory.getResponseSpec()
                        )

                        .statusCode(200)

                        .extract()

                        .response();



        return response.as(
                LoginResponse.class
        );

    }

}