package org.sdet.API_Framework.clients;

import io.restassured.response.Response;
import org.sdet.API_Framework.base.BaseAPI;
import org.sdet.API_Framework.constants.Endpoints;
import org.sdet.API_Framework.model.request.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseAPI {


    public Response login(LoginRequest request) {

        return postWithoutToken(
                Endpoints.LOGIN,
                request
        );

    }
}