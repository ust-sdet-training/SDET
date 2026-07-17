package API_FrameWork.service;

import API_FrameWork.Factory.RequestSpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.LoginRequest;
import API_FrameWork.models.LoginResponse;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public LoginResponse login(String email, String password) {

        LoginRequest request = new LoginRequest(email, password);

        Response response =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .body(request)
                        .when()
                        .post(EndPoints.LOGIN)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        return response.as(LoginResponse.class);
    }
}