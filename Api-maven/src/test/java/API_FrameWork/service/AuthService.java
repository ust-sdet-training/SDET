package API_FrameWork.service;

import API_FrameWork.Factory.SpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.LoginRequest;
import API_FrameWork.models.LoginResponse;

import static io.restassured.RestAssured.given;

public class AuthService {
    public LoginResponse login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return given()
                .spec(SpecFactory.requestSpec())
                .body(request)
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }
}